package com.lab1917tapoimarius.Service;

import com.lab1917tapoimarius.Exception.NotFoundException;
import com.lab1917tapoimarius.Model.Game;
import com.lab1917tapoimarius.Repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GameService {
    @Autowired
    private GameRepository gameRepository;

    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public Game getGameById(Long id) {
        return gameRepository.findById(id).orElseThrow(() ->new NotFoundException(id));
    }
    public List<Game> getAllGames(){
        return gameRepository.findAll();
    }

    public void addGame(Game newGame){
        gameRepository.save(newGame);
    }

    public void updateGame(Game newSmartphone, Long id){
        gameRepository.findById(id).map(game -> {
            game.setName(newSmartphone.getName());
            game.setGenre(newSmartphone.getGenre());
            game.setModes(newSmartphone.getModes());
            game.setYearOfRelease(newSmartphone.getYearOfRelease());
            game.setPrice(newSmartphone.getPrice());
            return gameRepository.save(game);
        }).orElseGet(()->{
            newSmartphone.setId(id);
            return gameRepository.save(newSmartphone);
        });
    }

    public void deleteGame(Long id){
        gameRepository.deleteById(id);
    }
    public List<Game> getGameWithPriceHigherThanGivenValue(Double price){
        return gameRepository.findAll().stream().filter(smartphone -> smartphone.getPrice() > price)
                .collect(Collectors.toList());
    }
}
