package code;


public class Tile extends Sprite {

	private static final Properties[] types;

	private record Properties(String fileName, int isSolid) {

		public int isSolidType() {
			return isSolid;
		}
	}

	static {
		types = new Properties[0xFF];
		types[0x01] = new Properties("Woodfloor1.png"  , 0);
		types[0x02] = new Properties("Stonebrick1.png" , 1);
		types[0x03] = new Properties("Stonebrick2.png" , 1);
		types[0x04] = new Properties("Stonebrick3.png" , 1);
		types[0x05] = new Properties("Stonebrick4.png" , 1);
		types[0x06] = new Properties("Stonebrick5.png" , 1);
		types[0x07] = new Properties("Stonebrick6.png" , 1);
		types[0x08] = new Properties("Bed1.png"        , 1);
		types[0x09] = new Properties("Table1.png"      , 1);
		types[0x0A] = new Properties("Bed2.png"        , 1);
		types[0x0B] = new Properties("Brazier1.gif"    , 1);
		types[0x0C] = new Properties("Table2.png"      , 1);
		types[0x0D] = new Properties("Leveldoor1.png"  , 1);
		types[0x0E] = new Properties("Stonebrick1S.png", 1);
		types[0x0F] = new Properties("Woodfloor1S.png" , 1);
		types[0x10] = new Properties("Leveldoor2.png"  , 1);
		types[0x11] = new Properties("Leveldoor3.png"  , 1);
		types[0x12] = new Properties("Leveldoor4.png"  , 1);
		types[0x13] = new Properties("DirtPath.png"    , 0);
		types[0x14] = new Properties("Grass1.png"      , 0);
		types[0x15] = new Properties("Water1.gif"      , 0);
		types[0x16] = new Properties("RoyalCarpetV.png", 0);
		types[0x17] = new Properties("Table3.png"      , 1);
		types[0x18] = new Properties("ChairUp.png"     , 1);
		types[0x19] = new Properties("ChairDown.png"   , 1);
		types[0x1A] = new Properties("ChairLeft.png"   , 1);
		types[0x1B] = new Properties("ChairRight.png"  , 1);
		types[0x1C] = new Properties("Table4.png"      , 1);
		types[0x1D] = new Properties("Table5.png"      , 1);
		types[0x1E] = new Properties("Table6.png"      , 1);
		types[0x1F] = new Properties("StonebrickW1.png", 1);
		types[0x20] = new Properties("StonebrickW2.png", 1);
		types[0x21] = new Properties("StonebrickW3.png", 1);
		types[0x22] = new Properties("Crate1.png"      , 1);
		types[0x23] = new Properties("RoyalCarpetH.png", 0);
		types[0x24] = new Properties("RoyalCarpetC.png", 0);
		types[0x25] = new Properties("StoneRoad.png"   , 0);
		types[0x26] = new Properties("KingsThrone.png" , 1);
	}

	/**
	 * Tile constructor
	 * @param x X position
	 * @param y Y position
	 * @param type Tile ID
	 */
	public Tile(int x, int y, int type) {
		super(x, y, type);
		
		if (type > 0 && type < 99) {
			loadImage(types[type].fileName());
			solid = types[type].isSolidType();			
		}
		else if (type == 99) {
			// type 99 represents empty tile
			loadImage(null);
			solid = 1;
		}
		else {
			loadImage("UntexturedTile.png");
			solid = 0;
		}
	}

	/**
	 * Returns the tile ID.
	 */
	public int getType() {
		return type;
	}
}
