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

<<<<<<< HEAD
				public Option() {}
			}
			private  String text;
			private Option[] options;

			public Frame() {}
		}
		private Frame[] frames;
	}
=======
	public Dialogue(String textFilePath) {}
>>>>>>> origin/main

	Descriptor d;
	int f = 0;

	// See https://github.com/google/gson/blob/main/UserGuide.md

	public Dialogue(String str) {
		var gson = new Gson();
		d = gson.fromJson(str, Descriptor.class);


		//RECIEVE INPUT, ASK TONY IF WE USE "KEYPRESS" OR SCAN IT IN
		while(f!=-1)
		{
			step();
		}



	}

	public void step() {
		System.out.println("Descriptor:");
		System.out.println(d.frames[f].text);
		System.out.println();
		for(int i = 0; i < d.frames[f].options.length; i++) {
			System.out.println(d.frames[f].options[i].text);
		}
		int o = new Scanner(System.in).nextInt()-1;

		if(d.frames[f].options[o].action.equals("sellShield"))
		{
			//add to inventory
			Player.subtractCoins(200); //later check to make sure there's enough money
			System.out.println("TEST");
			f = d.frames[f].options[o].next;
		}
		else if(d.frames[f].options[o].action.equals("sellArrow"))
		{
			//add to inventory
			Player.subtractCoins(100); //later check to make sure there's enough money
			System.out.println("TEST 2");
			f = d.frames[f].options[o].next;
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


	}


	public static void main(String[] args) throws IOException {
		new Dialogue(Files.readString(Path.of(System.getProperty("user.dir") + "/src/Dialogues/Dialog1.json")));
	}
}
