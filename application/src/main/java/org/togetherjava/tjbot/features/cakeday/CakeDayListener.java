package org.togetherjava.tjbot.features.cakeday;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import org.togetherjava.tjbot.features.EventReceiver;


/**
 * A listener class responsible for handling cake day related events.
 */
public class CakeDayListener extends ListenerAdapter implements EventReceiver {

    private final CakeDayService cakeDayService;

    public CakeDayListener(CakeDayService cakeDayService) {
        this.cakeDayService = cakeDayService;
    }

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        Guild guild = event.getGuild();
        Member member = event.getMember();

        cakeDayService.insertUserCakeDay(member, guild);
    }

    /**
     * Handles the event of a guild member being removed from the guild. It removes the user's cake
     * day information from the database if present.
     *
     * @param event the {@link GuildMemberRemoveEvent} representing the member removal event
     */
    @Override
    public void onGuildMemberRemove(GuildMemberRemoveEvent event) {
        User user = event.getUser();
        Guild guild = event.getGuild();

        cakeDayService.removeUserCakeDay(user, guild);
    }
}
