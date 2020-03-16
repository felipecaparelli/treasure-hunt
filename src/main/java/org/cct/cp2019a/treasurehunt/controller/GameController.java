package org.cct.cp2019a.treasurehunt.controller;

import org.cct.cp2019a.treasurehunt.enumeration.GameStatus;
import org.cct.cp2019a.treasurehunt.exception.GameRuleException;
import org.cct.cp2019a.treasurehunt.model.Game;
import org.cct.cp2019a.treasurehunt.model.GameBoard;
import org.cct.cp2019a.treasurehunt.model.PlayerSlot;
import org.cct.cp2019a.treasurehunt.util.GameUtils;
import org.cct.cp2019a.treasurehunt.validator.GameValidator;
import org.cct.cp2019a.treasurehunt.view.GameData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class GameController {

    private GameValidator gameValidator = new GameValidator();

    private Game game;

    @GetMapping(value = "/index")
    public String index(Model model) {
        model.addAttribute("gameStartData", new GameData());
        return "index";
    }

    @PostMapping("/index")
    public String indexSubmit(@ModelAttribute GameData gameData, Model model) {
        try {
            if (gameValidator.isValidPlayerNum(gameData.getNumberOfPlayer())) {
                model.addAttribute("error", null);
                this.game = initGame();
                model.addAttribute("numberOfPlayer", gameData.getNumberOfPlayer());
                return "players";
            }
        } catch (GameRuleException e) {
            e.printStackTrace();
            model.addAttribute("error", e.getMessage());
        }
        return "index";
    }

    @GetMapping(value = "/players")
    public String players(@ModelAttribute Integer numberOfPlayer, Model model) {
        model.addAttribute("players", buildPlayerSlots(numberOfPlayer));
        return "players";
    }

    @RequestMapping(value = "/rules")
    public String gameRules() {
        return "rules";
    }

    // start
    private Game initGame() {
        GameBoard gameBoard = GameUtils.buildGameBoard();
        Game game = new Game(gameBoard);
        game.setGameStatus(GameStatus.STARTED);
        return game;
    }

    private List<PlayerSlot> buildPlayerSlots(int numberOfPlayers) {
        List<PlayerSlot> slots = new ArrayList<>(numberOfPlayers);
        for (int i = 0; i < numberOfPlayers; i++) {
            slots.add(new PlayerSlot());
        }
        return slots;
    }

    // do a player turn

    // finish the game
    @PostMapping("/index")
    public String finish() {
        return "finish";
    }
}
