package code;

import com.google.gson.*;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class Dialogue {

	public static class Descriptor {
		public static class Frame {
			public static class Option {
				private String text;
				private String action;
				private int next;

				public Option() {}
			}
			private  String text;
			private Option[] options;

			public Frame() {}
		}
		private Frame[] frames;
	}

	Descriptor d;
	int f = 0;
	int previous = 0;

	// See https://github.com/google/gson/blob/main/UserGuide.md

	public Dialogue(Player player, String str) {
		var gson = new Gson();
		d = gson.fromJson(str, Descriptor.class);

		while(f!=-1)
		{
			int o = new Scanner(System.in).nextInt()-1;
			step(player, o);
		}
	}

	public void step(Player player, int o) {
		System.out.println("Descriptor:");
		System.out.println(d.frames[f].text);
		System.out.println();
		for(int i = 0; i < d.frames[f].options.length; i++) {
			System.out.println(d.frames[f].options[i].text);
		}


		if(d.frames[f].options[o].action.equals("sellCrossbow"))
		{
			//add to inventory
			System.out.println("TEST");

			if(!Shop.buyItem(0)) {
				f = previous;
			}
			else
			{
				f = d.frames[f].options[o].next;
				player.subtractCoins(200);
				Inventory.addItem(Shop.returnItem(0));
			}
		}
		else if(d.frames[f].options[o].action.equals("sellSword"))
		{
			//add to inventory
			System.out.println("TEST 2");
			if(!Shop.buyItem(1)) {
				f = previous;
			}
			else
			{
				f = d.frames[f].options[o].next;
				player.subtractCoins(100);
				Inventory.addItem(Shop.returnItem(1));
			}
		}
		else if(d.frames[f].options[o].action.equals("sellKey"))
		{
			//add to inventory
			System.out.println("TEST 3");
			if(!Shop.buyItem(2)) {
				f = previous;
			}
			else
			{
				f = d.frames[f].options[o].next;
				player.subtractCoins(250);
				Inventory.addItem(Shop.returnItem(2));
			}
		}
		else if(d.frames[f].options[o].action.equals("goBack"))
		{
			//add to inventory
			f = 0;
		}
		else if(d.frames[f].options[o].action.equals("quit"))
		{
			f = d.frames[f].options[o].next;
		}

		previous = f;
	}
}
