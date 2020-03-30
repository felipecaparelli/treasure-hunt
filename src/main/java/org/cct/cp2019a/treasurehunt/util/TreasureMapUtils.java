package org.cct.cp2019a.treasurehunt.util;

import org.cct.cp2019a.treasurehunt.model.BoardSquare;
import org.cct.cp2019a.treasurehunt.model.Treasure;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This utility class is responsible for reading the location of the treasure from Pirate Pete’s secret journal.
 */
public class TreasureMapUtils {

    // Pirate Pete’s secret journal file name
    private static final String SECRET_JOURNAL_FILE_PATH = "PiratePete.txt";

    /**
     * Read the file to get the positions where the treasures must be hidden.
     * @return the list with the positions where the treasures must be hidden
     */
    protected static List<BoardSquare> readPiratePetesSecretJournal() {
        List<String> columnNames = readColumnNames();
        List<Integer> rowNumber = readRowNumbers();

        try {

            assert columnNames != null;
            List<BoardSquare> positions = new ArrayList<>(columnNames.size());

            for (int i=0; i < columnNames.size(); i++) {
                String columnName = columnNames.get(i);
                assert rowNumber != null;
                positions.add(new BoardSquare(columnName, rowNumber.get(i), generateRandomTreasure()));
            }

            return positions;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * This method read the column names inside the secret journal file.
     * @return the list with column names
     */
    private static List<String> readColumnNames () {
        try (Stream<String> stream = loadSecretJournalFile())
        {
            return stream
                    .filter(StringUtils::isLetter)
                    .collect(Collectors.toList());
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        throw new IllegalStateException("Treasure map rows not found");
    }

    /**
     * This method read the row numbers inside the secret journal file.
     * @return the list with row numbers
     */
    private static List<Integer> readRowNumbers () {
        try (Stream<String> stream = loadSecretJournalFile())
        {
            return stream
                    .filter(NumberUtils::isInteger)
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        throw new IllegalStateException("Treasure map rows not found");
    }

    /**
     * Read file content and convert it to a data stream.
     * @return {@link Stream}
     * @throws URISyntaxException
     * @throws IOException
     */
    private static Stream<String> loadSecretJournalFile() throws URISyntaxException, IOException {
        return Files.lines(getSecretJournalPath(), StandardCharsets.UTF_8);
    }

    /**
     * This method returns the secret journal file path
     * @return {@link Path}
     * @throws URISyntaxException
     */
    private static Path getSecretJournalPath() throws URISyntaxException {
        return Paths.get(ClassLoader.getSystemResource(SECRET_JOURNAL_FILE_PATH).toURI());
    }

    /**
     * This method should generates a treasure randomly.
     * @return {@link Treasure}
     */
    private static Treasure generateRandomTreasure() {
        return new Treasure(NumberUtils.getRandomNumberInRange(20, 40));
    }

}
