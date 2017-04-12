package org.craftsmenlabs.stories.isolator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import mockit.Tested;
import org.apache.commons.io.FileUtils;
import org.craftsmenlabs.stories.api.models.items.base.Backlog;
import org.craftsmenlabs.stories.api.models.items.base.Feature;
import org.craftsmenlabs.stories.isolator.model.github.GithubJsonIssue;
import org.craftsmenlabs.stories.isolator.parser.GithubJsonParser;
import org.junit.Test;

import java.io.File;
import java.net.URL;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class GithubJsonParserTest
{

    private ObjectMapper mapper = new ObjectMapper();

    @Tested
    private GithubJsonParser githubJsonParser;

    @Test
    public void getIssuesTest() throws Exception {
        Backlog backlog = githubJsonParser.parse(mapper.readValue(readFile("github-integration-test.json"), mapper.getTypeFactory().constructCollectionType(LinkedList.class, GithubJsonIssue.class)));

        assertThat(backlog.getIssues()).containsOnlyKeys("1", "2");
    }

    @Test
    public void getIssuesReturnsLexicographicallySortableRanks() {

        List<GithubJsonIssue> githubJsonIssues = Lists.newArrayList(
                GithubJsonIssue.builder().id(0).title("").body("").build(),
                GithubJsonIssue.builder().id(1).title("").body("").build(),
                GithubJsonIssue.builder().id(2).title("").body("").build(),
                GithubJsonIssue.builder().id(3).title("").body("").build(),
                GithubJsonIssue.builder().id(4).title("").body("").build(),
                GithubJsonIssue.builder().id(5).title("").body("").build(),
                GithubJsonIssue.builder().id(6).title("").body("").build(),
                GithubJsonIssue.builder().id(7).title("").body("").build(),
                GithubJsonIssue.builder().id(8).title("").body("").build(),
                GithubJsonIssue.builder().id(9).title("").body("").build(),
                GithubJsonIssue.builder().id(10).title("").body("").build(),
                GithubJsonIssue.builder().id(100).title("").body("").build()

        );

        Backlog backlog = githubJsonParser.parse(githubJsonIssues);

        assertThat(backlog.getIssues().entrySet().stream()
                .map(item -> (Feature) item.getValue())
                .sorted(Comparator.comparing(Feature::getRank))
                .collect(Collectors.toList())).containsExactly(
                Feature.builder().key("0").rank("00").userstory("").acceptanceCriteria("").estimation(0.0).build(),
                Feature.builder().key("1").rank("01").userstory("").acceptanceCriteria("").estimation(0.0).build(),
                Feature.builder().key("2").rank("02").userstory("").acceptanceCriteria("").estimation(0.0).build(),
                Feature.builder().key("3").rank("03").userstory("").acceptanceCriteria("").estimation(0.0).build(),
                Feature.builder().key("4").rank("04").userstory("").acceptanceCriteria("").estimation(0.0).build(),
                Feature.builder().key("5").rank("05").userstory("").acceptanceCriteria("").estimation(0.0).build(),
                Feature.builder().key("6").rank("06").userstory("").acceptanceCriteria("").estimation(0.0).build(),
                Feature.builder().key("7").rank("07").userstory("").acceptanceCriteria("").estimation(0.0).build(),
                Feature.builder().key("8").rank("08").userstory("").acceptanceCriteria("").estimation(0.0).build(),
                Feature.builder().key("9").rank("09").userstory("").acceptanceCriteria("").estimation(0.0).build(),
                Feature.builder().key("10").rank("10").userstory("").acceptanceCriteria("").estimation(0.0).build(),
                Feature.builder().key("100").rank("11").userstory("").acceptanceCriteria("").estimation(0.0).build()

        );
    }

    private String readFile(String resource) throws Exception {
        URL url = this.getClass().getClassLoader().getResource(resource);
        return FileUtils.readFileToString(new File(url.toURI()), "UTF-8");
    }
}
