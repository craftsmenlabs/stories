package org.craftsmenlabs.stories.isolator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import mockit.Tested;
import org.apache.commons.io.FileUtils;
import org.craftsmenlabs.stories.api.models.items.base.Backlog;
import org.craftsmenlabs.stories.api.models.items.base.Feature;
import org.craftsmenlabs.stories.isolator.model.trello.TrelloJsonIssue;
import org.craftsmenlabs.stories.isolator.parser.TrelloJsonParser;
import org.junit.Test;

import java.io.File;
import java.net.URL;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class TrelloJsonParserTest {

    private ObjectMapper mapper = new ObjectMapper();
    @Tested
    private TrelloJsonParser trelloJsonParser;

    @Test
    public void getIssuesTest() throws Exception {
        Backlog backlog = trelloJsonParser.parse(mapper.readValue(readFile("trello-integration-test.json"), mapper.getTypeFactory().constructCollectionType(LinkedList.class, TrelloJsonIssue.class)));

        assertThat(backlog.getIssues()).containsOnlyKeys("581b199ba7dfd7e8f737262c");
    }

    @Test
    public void getIssuesReturnsLexicographicallySortableRanks() {

        List<TrelloJsonIssue> trelloJsonIssues = Lists.newArrayList(
                TrelloJsonIssue.builder().id("0").name("").desc("").build(),
                TrelloJsonIssue.builder().id("1").name("").desc("").build(),
                TrelloJsonIssue.builder().id("2").name("").desc("").build(),
                TrelloJsonIssue.builder().id("3").name("").desc("").build(),
                TrelloJsonIssue.builder().id("4").name("").desc("").build(),
                TrelloJsonIssue.builder().id("5").name("").desc("").build(),
                TrelloJsonIssue.builder().id("6").name("").desc("").build(),
                TrelloJsonIssue.builder().id("7").name("").desc("").build(),
                TrelloJsonIssue.builder().id("8").name("").desc("").build(),
                TrelloJsonIssue.builder().id("9").name("").desc("").build(),
                TrelloJsonIssue.builder().id("10").name("").desc("").build(),
                TrelloJsonIssue.builder().id("100").name("").desc("").build()

        );

        Backlog backlog = trelloJsonParser.parse(trelloJsonIssues);

        assertThat(backlog.getIssues().entrySet().stream()
                .map(item -> (Feature) item.getValue())
                .sorted(Comparator.comparing(Feature::getRank))
                .collect(Collectors.toList())).containsExactly(
                Feature.builder().summary("").key("0").rank("00").userstory("").acceptanceCriteria("").estimation(0.0).build(),
                Feature.builder().summary("").key("1").rank("01").userstory("").acceptanceCriteria("").estimation(0.0).build(),
                Feature.builder().summary("").key("2").rank("02").userstory("").acceptanceCriteria("").estimation(0.0).build(),
                Feature.builder().summary("").key("3").rank("03").userstory("").acceptanceCriteria("").estimation(0.0).build(),
                Feature.builder().summary("").key("4").rank("04").userstory("").acceptanceCriteria("").estimation(0.0).build(),
                Feature.builder().summary("").key("5").rank("05").userstory("").acceptanceCriteria("").estimation(0.0).build(),
                Feature.builder().summary("").key("6").rank("06").userstory("").acceptanceCriteria("").estimation(0.0).build(),
                Feature.builder().summary("").key("7").rank("07").userstory("").acceptanceCriteria("").estimation(0.0).build(),
                Feature.builder().summary("").key("8").rank("08").userstory("").acceptanceCriteria("").estimation(0.0).build(),
                Feature.builder().summary("").key("9").rank("09").userstory("").acceptanceCriteria("").estimation(0.0).build(),
                Feature.builder().summary("").key("10").rank("10").userstory("").acceptanceCriteria("").estimation(0.0).build(),
                Feature.builder().summary("").key("100").rank("11").userstory("").acceptanceCriteria("").estimation(0.0).build()

        );
    }

    private String readFile(String resource) throws Exception {
        URL url = this.getClass().getClassLoader().getResource(resource);
        return FileUtils.readFileToString(new File(url.toURI()), "UTF-8");
    }
}
