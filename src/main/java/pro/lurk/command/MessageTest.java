package pro.lurk.command;

import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.MessageDeleteEvent;
import net.dv8tion.jda.core.events.message.MessageUpdateEvent;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class MessageTest extends ListenerAdapter {

	@Override
	public void onMessageUpdate(MessageUpdateEvent event) {
		Message message = event.getMessage();
		
		System.out.printf("Message edited to: %s\n", message.getContentDisplay());
	}
	@Override
	public void onMessageDelete(MessageDeleteEvent event) {
//		Message message = event.getMessage();
		System.out.println("Message deleted!");
	}
	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
//		Message message = event.getMessage();
		System.out.println("Message deleted!");
	}
}
