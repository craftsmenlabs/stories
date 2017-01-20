package org.craftsmenlabs.stories.isolator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import mockit.Tested;
import org.apache.commons.io.FileUtils;
import org.craftsmenlabs.stories.api.models.scrumitems.Backlog;
import org.craftsmenlabs.stories.api.models.scrumitems.Feature;
import org.craftsmenlabs.stories.isolator.model.trello.TrelloJsonIssue;
import org.craftsmenlabs.stories.isolator.parser.TrelloJsonParser;
import org.junit.Test;

import java.io.File;
import java.net.URL;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TrelloJsonParserTest {

    private ObjectMapper mapper = new ObjectMapper();

    @Tested
    private TrelloJsonParser trelloJsonParser;

    @Test
    public void testParseWorksOk() {
        List<TrelloJsonIssue> trelloJsonIssues = Lists.newArrayList(
                TrelloJsonIssue.builder().id("0").name("name").desc("story.\nacceptance criteria").build()
        );

        Backlog backlog = trelloJsonParser.parse(trelloJsonIssues);
        backlog.getFeatures().sort(Comparator.comparing(Feature::getRank));

        assertThat(backlog.getFeatures()).containsExactly(
                Feature.builder().key("0").rank("0").summary("name").userstory("story.").acceptanceCriteria("acceptance criteria").estimation(0f).build()
        );
    }

    @Test
    public void testParseWithFile() throws Exception {
        Backlog backlog = trelloJsonParser.parse(mapper.readValue(readFile("trello-integration-test.json"), mapper.getTypeFactory().constructCollectionType(LinkedList.class, TrelloJsonIssue.class)));

        assertThat(backlog.getFeatures()).extracting(Feature::getKey).containsOnly("581b199ba7dfd7e8f737262c");
    }

    @Test
    public void testParseReturnsLexicographicallySortableRanks() {

        List<TrelloJsonIssue> trelloJsonIssues = Lists.newArrayList(
                TrelloJsonIssue.builder().id("0").desc("").build(),
                TrelloJsonIssue.builder().id("1").desc("").build(),
                TrelloJsonIssue.builder().id("2").desc("").build(),
                TrelloJsonIssue.builder().id("3").desc("").build(),
                TrelloJsonIssue.builder().id("4").desc("").build(),
                TrelloJsonIssue.builder().id("5").desc("").build(),
                TrelloJsonIssue.builder().id("6").desc("").build(),
                TrelloJsonIssue.builder().id("7").desc("").build(),
                TrelloJsonIssue.builder().id("8").desc("").build(),
                TrelloJsonIssue.builder().id("9").desc("").build(),
                TrelloJsonIssue.builder().id("10").desc("").build(),
                TrelloJsonIssue.builder().id("100").desc("").build()

        );

        Backlog backlog = trelloJsonParser.parse(trelloJsonIssues);
        backlog.getFeatures().sort(Comparator.comparing(Feature::getRank));

        assertThat(backlog.getFeatures()).containsExactly(
                Feature.builder().key("0").rank("00").estimation(0f).build(),
                Feature.builder().key("1").rank("01").estimation(0f).build(),
                Feature.builder().key("2").rank("02").estimation(0f).build(),
                Feature.builder().key("3").rank("03").estimation(0f).build(),
                Feature.builder().key("4").rank("04").estimation(0f).build(),
                Feature.builder().key("5").rank("05").estimation(0f).build(),
                Feature.builder().key("6").rank("06").estimation(0f).build(),
                Feature.builder().key("7").rank("07").estimation(0f).build(),
                Feature.builder().key("8").rank("08").estimation(0f).build(),
                Feature.builder().key("9").rank("09").estimation(0f).build(),
                Feature.builder().key("10").rank("10").estimation(0f).build(),
                Feature.builder().key("100").rank("11").estimation(0f).build()

        );
    }

    private String readFile(String resource) throws Exception {
        URL url = this.getClass().getClassLoader().getResource(resource);
        return FileUtils.readFileToString(new File(url.toURI()), "UTF-8");
    }
}
