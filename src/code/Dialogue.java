package code;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@SuppressWarnings("unused")
public class Dialogue{
	
	int currentSection;
	
	public class Section{
		String start;
		
		String choice1;
		int destination1;
		String choice2;
		int destination2;
		
		int selection;
		
		public Section(String s, String c1, String c2, int d1, int d2, int sl) {
			start = s; choice1 = c1; choice2 = c2;
			destination1 = d1;
			destination2 = d2;
			selection = sl;
		}
		
		public String getSectionText() {
			return start;
		}
		
		public String getChoice1Text() {
			return choice1;
		}
		
		public String getChoice2Text() {
			return choice2;
		}
		
		public int getD1() {
			return destination1;
		}
		
		public int getD2() {
			return destination2;
		}
		
		public int getSelection() {
			return selection;
		}
		
		public void setSelection(int s) {
			selection = s;
		}
	}
	
	List<Section> sections = new ArrayList<Section>();
	
public Dialogue(String textFilePath) {
		currentSection = 0;
File file = new File(textFilePath);
try {
	Scanner scanner = new Scanner(file);
		
	boolean inSection = false;
	boolean inChoice1 = false;
	boolean inChoice2 = false;
			
	boolean sTextComplete = false;
	boolean sComplete = false;
	
	boolean c1TextComplete = false;
	boolean c1Complete = false;
	
	boolean c2TextComplete = false;
	boolean c2Complete = false;
	
	boolean finallyDone = false;
	
	int sect; int chs;
			
	String s = "";
	String c1 = "";
	String c2 = "";
	int d1 = 0;
	int d2 = 0;
	
while(scanner.hasNextLine()) {
		String data = scanner.nextLine();
				
	if(!inSection) {
					
		if(data.contains("SECTION")) {
		sComplete = true;
		System.out.println("Dialogue: Section name found");
		}
		else {
			
			char[] c = new char[data.length()];
			for(int i = 0; i < data.length() - 1; i++) {
				c[i] = data.charAt(i);
			}
			
			s = String.valueOf(c);
			
			System.out.println("Dialogue: Section text found");
			sTextComplete = true;
		}
		
		if(sTextComplete && sComplete)
			inSection = true;
	}
				
	if(inSection) {
		
		if(!inChoice1 && !inChoice2) {
			if(data.contains("-CHOICE1")) {
			inChoice1 = true;
			System.out.println("Dialogue: Found choice 1 ");
			continue;
			}
		}
					
		if(!inChoice2 && inChoice1) {
			if(data.contains("-CHOICE2")) {
			inChoice2 = true;
			inChoice1 = false;
			System.out.println("Dialogue: Found choice 2 ");
			continue;
			}
		}
				
		if(inChoice1 && !inChoice2) {
			
			if(data.contains("-GOTO")) {
				
				char[] c = data.toCharArray();
				d1 = Integer.parseInt(String.valueOf(c[c.length - 1]));
				
				c1Complete = true;
				System.out.println("Dialogue: Found goto for choice 1: "+d1);
				
				if(c1Complete && c1TextComplete && c2Complete && c2TextComplete) {
					sections.add(new Section(s, c1, c2, d1, d2, 1));
					
					inSection = false;inChoice1 = false;inChoice2 = false;	
					sTextComplete = false;sComplete = false;c1TextComplete = false; 
					c1Complete = false; c2TextComplete = false; c2Complete = false;
					
					sect = 0; chs = 0; s = "";c1 = "";c2 = "";d1 = 0;d2 = 0;
				}	
				
				continue;
			}
			
			else {
				
				char[] c = new char[data.length()];
				for(int i = 0; i < data.length() - 1; i++) {
						c[i] = data.charAt(i);
				}
					c1 = String.valueOf(c);
				c1TextComplete = true;
				System.out.println("Dialogue: Found text for choice 1: "+data);
				
				if(c1Complete && c1TextComplete && c2Complete && c2TextComplete) {
					sections.add(new Section(s, c1, c2, d1, d2, 1));
					
					inSection = false;inChoice1 = false;inChoice2 = false;
					sTextComplete = false;sComplete = false;c1TextComplete = false; 
					c1Complete = false; c2TextComplete = false; c2Complete = false;
					
					sect = 0; chs = 0; s = "";c1 = "";c2 = "";d1 = 0;d2 = 0;
				}	
				
				continue;
			}
		}
		
		if(inChoice2 && !inChoice1) {
			
			if(data.contains("-GOTO")) {
				
				char[] c = data.toCharArray();
				d2 = Integer.parseInt(String.valueOf(c[c.length - 1]));
				
				c2Complete = true;
				System.out.println("Dialogue: Found goto for choice 2: "+d2);
				
				if(c1Complete && c1TextComplete && c2Complete && c2TextComplete) {
					sections.add(new Section(s, c1, c2, d1, d2, 1));
					
					inSection = false;inChoice1 = false;inChoice2 = false;
					sTextComplete = false;sComplete = false;c1TextComplete = false; 
					c1Complete = false; c2TextComplete = false; c2Complete = false;
					
					sect = 0; chs = 0; s = "";c1 = "";c2 = "";d1 = 0;d2 = 0;
				}	
				
				continue;
			}
			
			else {
				
				char[] c = new char[data.length()];
				for(int i = 0; i < data.length() - 1; i++) {
					c[i] = data.charAt(i);
				}
				
					c2 = String.valueOf(c);
				c2TextComplete = true;
				System.out.println("Dialogue: Found text for choice 2: "+data);
				
				if(c1Complete && c1TextComplete && c2Complete && c2TextComplete) {
					sections.add(new Section(s, c1, c2, d1, d2, 1));
					
					inSection = false;inChoice1 = false;inChoice2 = false;
					sTextComplete = false;sComplete = false;c1TextComplete = false; 
					c1Complete = false; c2TextComplete = false; c2Complete = false;
					
					sect = 0; chs = 0; s = "";c1 = "";c2 = "";d1 = 0;d2 = 0;
				}	
				
				continue;
			}
		}

	}	
	
}

System.out.println("Dialogue: Contains "+sections.size()+" sections");

scanner.close();

} catch (FileNotFoundException e) {
	e.printStackTrace();
}
		
}

public void showDialog(Player p, Graphics2D buffer, int x, int y, int width, int height) {
	
	
	Section section = sections.get(currentSection);
	
	if(section != null) {
	
	Color color1 = Color.BLACK;
	Color color2 = Color.BLACK;
	
	if(p.isUpArrow() && section.getSelection() == 2) {
		section.setSelection(1);
		
	}
	if(p.isDownArrow() && section.getSelection() == 1) {
		section.setSelection(2);	
		
	}
	
	//Yellow highlight to selected choices
	
	if(section.getSelection() == 1) {
		color1 = new Color(255, 255, 0);
		color2 = Color.BLACK;
	}

	if(section.getSelection() == 2) {
		color1 = Color.BLACK;
		color2 = new Color(255, 255, 0);
	}

	
	Menu.showDialogBox(x, y, width, height, buffer,
			section.getSectionText(), Color.BLACK, 
			section.getChoice1Text(), color1,
			section.getChoice2Text(), color2);
	
	//Prevents the user from holding down the enter key to spam selections
	//and instantly complete the dialogue
	
	if(p.isEnterKey()) {
		if(section.getSelection() == 1) {
			
			p.allowEnterKey(false);
			currentSection = section.getD1();
		}
		if(section.getSelection() == 2) {
			
			p.allowEnterKey(false);
			currentSection = section.getD2();
		}

	}
	}
}

}