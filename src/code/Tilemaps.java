package code;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

@SuppressWarnings("unused")
public class Tilemaps {
  
	private static String[] mapFiles = new String[256];
	private static int[][] loadedMapArrays = new int[256][];
	
	public static void init() {
		
		mapFiles[94 ] = "map_094.txt";
		mapFiles[95 ] = "map_095.txt";
		mapFiles[110] = "map_110.txt";
		mapFiles[111] = "map_111.txt";
		mapFiles[112] = "map_112.txt";
		mapFiles[113] = "map_113.txt";
		mapFiles[126] = "map_126.txt";
		mapFiles[127] = "map_127.txt";
		mapFiles[128] = "map_128.txt";
		mapFiles[142] = "map_142.txt";
		mapFiles[143] = "map_143.txt";
		mapFiles[158] = "map_158.txt";
		mapFiles[159] = "map_159.txt";
		
	}

	//Loads from an external text file the array which represents the layout of tiles for each room
	
	public static int[] loadMap(int zone) 
	{
		File file = new File("src/Maps/"+mapFiles[zone]);
		try 
		{
			Scanner scanner = new Scanner(file);
			String data = "";
			
			int[] array = new int[240];
			int i = 0;			
			
			while(scanner.hasNextLine())
				data += scanner.nextLine();
			
			for(String string : data.split(", "))
				array[i++] = Integer.parseInt(string);
			
			scanner.close();
			
			loadedMapArrays[zone] = array;
			return array;
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
			
			loadedMapArrays[zone] = new int[] {0};
			return new int[] {0};
		}
		
	}
	
	//Accesses an index of the map of a loaded room
	public static int getMapIndex(int zone, int i) {
		return loadedMapArrays[zone][i];
	}
	
	//Removes all the existing information of maps stored
	//This function is called before a new area is loaded,
	//to have previous maps cleared.
	
	public static void clearMap() {
		for(int i = 0; i < loadedMapArrays.length; i++) {
			loadedMapArrays[i] = new int[] {0};
		}
	}
	
  
  public static int[] getConnectedRooms(int zone) {
  switch(zone)
  {
  case 94:  return new int[] {94, 110, 126};
  case 95:  return new int[] {95};
  case 110: return new int[] {94, 110, 126};
  case 111: return new int[] {111, 127};
  case 112: return new int[] {112}; 
  case 113: return new int[] {113}; 
  case 126: return new int[] {126, 110, 94};
  case 127: return new int[] {127, 111};
  case 128: return new int[] {128};
  case 142: return new int[] {142, 143, 158, 159};
  case 143: return new int[] {142, 143, 158, 159};
  default: return null;
  }
  }
	
}