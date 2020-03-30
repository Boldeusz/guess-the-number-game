package org.marcin.controller;

import lombok.extern.slf4j.Slf4j;
import org.marcin.service.GameService;
import org.marcin.util.AttributeNames;
import org.marcin.util.GameMappings;
import org.marcin.util.ViewNames;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller

public class GameController {

    // == fields ==
    private final GameService gameService;

    // == constructor ==


    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    // == request methods ==
    @GetMapping(GameMappings.PLAY)
    public String play(Model model){
        model.addAttribute(AttributeNames.MAIN_MESSAGE, gameService.getMainMessage());
        model.addAttribute(AttributeNames.RESULT_MESSAGE, gameService.getResultMessage());
        log.info("model={}", model);

        if (gameService.isGameOver()){
            return ViewNames.GAME_OVER;
        }
        return ViewNames.PLAY;
    }
    @PostMapping(GameMappings.PLAY)
    public String processMessage(@RequestParam int guess){
        log.info("guess={}",guess);
        gameService.checkGuess(guess);
        return GameMappings.REDIRECT_PLAY;
    }
    @GetMapping(GameMappings.RESTART)
    public String restart(){
        gameService.reset();
        return GameMappings.REDIRECT_PLAY;
    }
}
