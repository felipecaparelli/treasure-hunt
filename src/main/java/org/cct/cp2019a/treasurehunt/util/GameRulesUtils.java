package org.cct.cp2019a.treasurehunt.util;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * This class manages the Game Rules text content.
 */
public class GameRulesUtils {

    private static final String GAME_RULES_FILE_PATH = "GameRules.txt";

    /**
     * Read file content and convert it to a data stream.
     * @return {@link Stream}
     * @throws URISyntaxException
     * @throws IOException
     */
    public static Stream<String> loadGameRulesFileContent() throws URISyntaxException, IOException {
        return Files.lines(getGameRulesFilePath(), StandardCharsets.UTF_8);
    }

    /**
     * This method returns the Game Rules file path
     * @return {@link Path}
     * @throws URISyntaxException
     */
    private static Path getGameRulesFilePath() throws URISyntaxException {
        return Paths.get(ClassLoader.getSystemResource(GAME_RULES_FILE_PATH).toURI());
    }
}
