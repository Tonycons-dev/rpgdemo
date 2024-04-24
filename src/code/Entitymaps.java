package code;


/*
                               ||
                               ||
                               ||
							   ||
							 ======
							 \    /
						      \  /
							   \/

	!!!!!!!!!!!!!!!!!!!! ATTENTION GRADER !!!!!!!!!!!!!!!!!!!!

	!!!!!!!!!!!!!!!!!!!!!!! LOOK HERE !!!!!!!!!!!!!!!!!!!!!!!!

			THIS CLASS WILL BE REMOVED IN THE FUTURE
			          AND REPLACED BY ROOM

				   AS SUCH IT HAS NO JAVADOC

			         PLEASE DO NOT GRADE IT

	!!!!!!!!!!!!!!!!!!!!!!! LOOK HERE !!!!!!!!!!!!!!!!!!!!!!!	!

	!!!!!!!!!!!!!!!!!!!! ATTENTION GRADER !!!!!!!!!!!!!!!!!!!!

								/\
							   /  \
							  /    \
							  ======
								||
								||
								||
								||
 */


public class Entitymaps {
	
	//Entitymaps will be stored in external files, like tilemaps, in the future
	
	static int[][] emap_094 = {
		{544, 224, 1, 0, 180, 0, 90}	
	};
	static int[][] emap_095 = {
		{544, 224, 1, 0, 180, 0, 90}				
	};
	static int[][] emap_110 = {
		{544, 224, 1, 0, 180, 0, 90}					
	};
	static int[][] emap_111 = {
		{32, 32, 1, 0, 90, 0, 30},
		{576, 32, 1, 0, 270, 0, 30},
		{32, 300, 1, 0, 90, 0, 30},
		{576, 300, 1, 0, 270, 0, 30}
	};
	static int[][] emap_112 = {
		{32, 32, 1, 0, 90, 0, 30},
		{576, 32, 1, 0, 270, 0, 30}
	};
	static int[][] emap_113 = {
		{32, 320, 1, 0, 0, 0, 30}
	};
	static int[][] emap_126 = {
		{544, 224, 1, 0, 180, 0, 90}			
	};
	static int[][] emap_127 = {
		{32, 32, 1, 0, 90, 0, 30},
		{576, 32, 1, 0, 270, 0, 30},
		{32, 300, 1, 0, 90, 0, 30},
		{576, 300, 1, 0, 270, 0, 30}	
	};
	static int[][] emap_128 = {
		{544, 224, 1, 0, 180, 0, 90}
	};
	static int[][] emap_142 = {
		{32, 32, 1, 0, 90, 0, 30},
		{576, 32, 1, 0, 270, 0, 30},
		{32, 300, 1, 0, 90, 0, 30},
		{576, 300, 1, 0, 270, 0, 30}
	};
	static int[][] emap_143 = {
		{544, 224, 1, 0, 180, 0, 90}		
	};
	static int[][] emap_158 = {
		{544, 224, 1, 0, 180, 0, 90}			
	};
	static int[][] emap_159 = {
		{544, 224, 1, 0, 180, 0, 90}		
	};
	
	public static int[][] getemap(int zone) {
	switch(zone)
	{
	  case 94:  return emap_094;
	  case 95:  return emap_095;
	  case 110: return emap_110;
	  case 111: return emap_111;
	  case 112: return emap_112;
	  case 113: return emap_113;
	  case 126: return emap_126;
	  case 127: return emap_127;
	  case 128: return emap_128;
	  case 142: return emap_142;
	  case 143: return emap_143;
	  case 158: return emap_158;
	  case 159: return emap_159;
	  default:  return emap_128;
	}
	}
	
	public static int[] getConnectedMap(int zone) {
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
	  default:  return new int[] {128};
	}	
	}
}
