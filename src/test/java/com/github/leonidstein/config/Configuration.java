package com.github.leonidstein.config;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "system:properties",
        "classpath:config.properties",
        "classpath:user.properties"
})
public interface Configuration extends Config {

    @Key("base.uri")
    String baseURI();

    @Key("base.path")
    String basePath();

    @Key("base.port")
    Integer basePort();

    @Key("login.test_user1.no_games")
    String loginTestUserHasNotGames();

    @Key("password.test_user1.no_games")
    String passwordTestUserHasNotGames();

    @Key("login.test_user2.has_game")
    String loginTestUserHasGame();

    @Key("password.test_user2.has_game")
    String passwordTestUserHasGame();

    @Key("login.test_user3.has_20_games")
    String loginTestUserHasTwentyGames();

    @Key("password.test_user3.has_20_games")
    String passwordTestUserHasTwentyGames();
}
