package org.togetherjava.tjbot.features.basic;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import org.togetherjava.tjbot.features.SlashCommand;
import org.togetherjava.tjbot.jda.JdaTester;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;

final class QuoteBoardForwarderTest {
    private JdaTester jdaTester;
    private SlashCommand command;

    private SlashCommandInteractionEvent triggerSlashCommand() {
        SlashCommandInteractionEvent event =
                jdaTester.createSlashCommandInteractionEvent(command).build();
        command.onSlashCommand(event);
        return event;
    }

    @BeforeEach
    void setUp() {
        jdaTester = new JdaTester();
        command = jdaTester.spySlashCommand(new PingCommand());
    }

    @Test
    @DisplayName("'/ping' responds with pong")
    void pingRespondsWithPong() {
        // GIVEN
        // WHEN using '/ping'
        SlashCommandInteractionEvent event = triggerSlashCommand();

        // THEN the bot replies with pong
        verify(event).reply("Pong!");
    }

    private static List<String> provideCountryEmojiCodes() {
        return List.of("\uD83C\uDDEC\uD83C\uDDF7", "\uD83C\uDDFA\uD83C\uDDF8",
                "\uD83C\uDDEF\uD83C\uDDF5", "\uD83C\uDDEE\uD83C\uDDF3");
    }

    private static List<String> provideNonCountryEmojiCodes() {
        return List.of("2910asd", "U+1F44D", "hi", "");
    }

    @ParameterizedTest
    @MethodSource("provideCountryEmojiCodes")
    @DisplayName("isCountryFlag should be true with valid country flags")
    void testIsCountryFlag(String countryFlag) {
        // GIVEN a valid country flag

        // WHEN tested to see if it is a country flag
        boolean isCountryFlag = QuoteBoardForwarder.isCountryFlag(countryFlag);

        assertTrue(isCountryFlag);
    }

    @ParameterizedTest
    @MethodSource("provideNonCountryEmojiCodes")
    @DisplayName("isCountryFlag should be false with non valid country flags")
    void testIsNotCountryFlag(String countryFlag) {
        // GIVEN a non-valid country flag

        // WHEN tested to see if it is a country flag
        boolean isCountryFlag = QuoteBoardForwarder.isCountryFlag(countryFlag);

        assertFalse(isCountryFlag);
    }
}
