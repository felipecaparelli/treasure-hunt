package org.cct.cp2019a.treasurehunt.controller;

import org.cct.cp2019a.treasurehunt.enumeration.GameStatus;
import org.cct.cp2019a.treasurehunt.exception.GameRuleException;
import org.cct.cp2019a.treasurehunt.model.*;
import org.cct.cp2019a.treasurehunt.util.GameUtils;
import org.cct.cp2019a.treasurehunt.util.ViewUtils;
import org.cct.cp2019a.treasurehunt.validator.GameValidator;
import org.cct.cp2019a.treasurehunt.view.DigAction;
import org.cct.cp2019a.treasurehunt.view.GameData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class GameController {

    private GameValidator gameValidator = new GameValidator();

    private static Game game;

    @GetMapping(value = "/index")
    public String index(Model model) {
        model.addAttribute("gameData", new GameData());
        return "index";
    }

    @PostMapping("/index")
    public String indexSubmit(@ModelAttribute GameData gameData, Model model) {
        try {
            if (gameValidator.isValidPlayerNum(gameData.getNumberOfPlayer())) {
                model.addAttribute("error", null);
                this.game = initGame(gameData.getNumberOfPlayer());
                model.addAttribute("player", new PlayerSlot());
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
        model.addAttribute("player", new PlayerSlot());
        return "players";
    }

    @PostMapping("/players")
    public String addPlayer(@ModelAttribute Player player, Model model) {
        boolean hasMorePlayerToAdd = true;
        try {

            if (game.hasAnyPlayerSlotAvailable()) {
                game.addPlayer(player);
                hasMorePlayerToAdd = game.hasAnyPlayerSlotAvailable();
            }
        } catch (GameRuleException e) {
            e.printStackTrace();
            model.addAttribute("error", e.getMessage());
        }

        return hasMorePlayerToAdd ? "players" : "rules";
    }

    @RequestMapping(value = "/rules")
    public String gameRules() {
        return "rules";
    }

    // start
    private Game initGame(int numberOfPlayers) {
        return new Game(GameUtils.buildGameBoard(), numberOfPlayers);
    }

    // start playing

    @GetMapping("/play")
    public String play(Model model) {
        try {
            game.start();
            GameBoard gameBoard = game.getGameBoard();
            model.addAttribute("headers", ViewUtils.buildViewGridHeaders(gameBoard.getGrid()));
            model.addAttribute("rows", ViewUtils.buildViewGridRows(gameBoard.getGrid()));
        } catch (GameRuleException e) {
            e.printStackTrace();
            model.addAttribute("errors", e.getMessage());
        }

        return "play";
    }

    @PostMapping(value = "/dig")
    public ResponseEntity dig(@RequestBody DigAction digAction, Model model) {
        Player player = game.getPlayers().get(digAction.getPlayer());
        player.dig();
        Map<String, List<BoardSquare>> grid = game.getGameBoard().getGrid();
        if (grid.containsKey(digAction.getCol())) {
            game.changeTurn();
            BoardSquare square = grid.get(digAction.getCol()).get(digAction.getRow());
            square.setDug(true);
            boolean hasTreasure = square.hasTreasure();
            return ResponseEntity.ok(hasTreasure ? HttpStatus.FOUND : HttpStatus.OK);
        } else {
            model.addAttribute("error", "Invalid grid position");
            return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
        }
    }

    // do a player turn

    // finish the game
    @PostMapping("/finish")
    public String finish() {
        return "finish";
    }
}
