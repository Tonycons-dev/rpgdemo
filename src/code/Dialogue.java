package code;

import com.google.gson.*;

import java.awt.event.KeyEvent;
import java.security.Key;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Dialogue {

	public static class Descriptor {
		public static class Frame {
			public static class Option {
				public String text;
				public String action;
				public int next;

				public Option() {}
			}
			public String text;
			public Option[] options;

			public Frame() {}
		}
		private Frame[] frames;
	}

	public Descriptor d;
	private int f = 0;
	private int previous = 0;

	public final String name;

	// See https://github.com/google/gson/blob/main/UserGuide.md

	public Dialogue(String name, Player player, String str) {
		this.name = name;
		var gson = new Gson();
		d = gson.fromJson(str, Descriptor.class);

//		while(f!=-1)
//		{
//			int o = new Scanner(System.in).nextInt()-1;
//			step(player, o);
//		}
	}

	private void buyItem(Player player, int o) {
		if(Shop.buyItem(player, o)) {
			f = 1;
		}
		else
		{
			f = 2;
		}
	}

	public void step(Player player, int o) {
		if (f == -1)
			return;

		o -= 1;
		if (o >= d.frames[f].options.length)
			return;
		if(d.frames[f].options[o].action.equals("goBack"))
		{
			f = previous;
		}
		else {
			previous = f;
			if(d.frames[f].options[o].action.equals("sellCrossbow"))
			{
				buyItem(player, 0);
			}
			else if(d.frames[f].options[o].action.equals("sellSword"))
			{
				buyItem(player, 1);
			}
			else if(d.frames[f].options[o].action.equals("sellKey"))
			{
				buyItem(player, 2);
			}
			else if(d.frames[f].options[o].action.equals("quit"))
			{
				f = -1;
			}
			else if (d.frames[f].options[o].action.equals("gameOver")) {
				throw new GameOverException();
			}
		}

	}

	public void keyPressed(Player player, KeyEvent e)
	{
		final Map<Integer, Integer> keyCodeToInt = new HashMap<>();
		keyCodeToInt.put(KeyEvent.VK_0, 0);
		keyCodeToInt.put(KeyEvent.VK_1, 1);
		keyCodeToInt.put(KeyEvent.VK_2, 2);
		keyCodeToInt.put(KeyEvent.VK_3, 3);
		keyCodeToInt.put(KeyEvent.VK_4, 4);
		keyCodeToInt.put(KeyEvent.VK_5, 5);
		keyCodeToInt.put(KeyEvent.VK_6, 6);
		keyCodeToInt.put(KeyEvent.VK_7, 7);
		keyCodeToInt.put(KeyEvent.VK_8, 8);
		keyCodeToInt.put(KeyEvent.VK_9, 9);

		Integer o = keyCodeToInt.get(e.getKeyCode());
		if (o != null)
			step(player, o);
	}

	public boolean isFinished() {
		return f == -1;
	}

	public Descriptor.Frame getCurrentFrame() {
		if (f < 0)
			return null;

		return d.frames[f];
	}
}
