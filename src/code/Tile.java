package code;


public class Tile extends Sprite {

	private static final Properties[] types;
	protected int type;


	private record Properties(String fileName, int isSolid) {

		public int isSolidType() {
			return isSolid;
		}
	}

	static {
		types = new Properties[0xFF];
		types[1] = new Properties("Woodfloor1.png"  , 0);
		types[2] = new Properties("Stonebrick1.png" , 1);
		types[3] = new Properties("Stonebrick2.png" , 1);
		types[4] = new Properties("Stonebrick3.png" , 1);
		types[5] = new Properties("Stonebrick4.png" , 1);
		types[6] = new Properties("Stonebrick5.png" , 1);
		types[7] = new Properties("Stonebrick6.png" , 1);
		types[8] = new Properties("Bed1.png"        , 1);
		types[9] = new Properties("Table1.png"      , 1);
		types[10] = new Properties("Bed2.png"        , 1);
		types[11] = new Properties("Brazier1.gif"    , 1);
		types[12] = new Properties("Table2.png"      , 1);
		types[13] = new Properties("Leveldoor1.png"  , 1);
		types[14] = new Properties("Stonebrick1S.png", 1);
		types[15] = new Properties("Woodfloor1S.png" , 1);
		types[16] = new Properties("Leveldoor2.png"  , 1);
		types[17] = new Properties("Leveldoor3.png"  , 1);
		types[18] = new Properties("Leveldoor4.png"  , 1);
		types[19] = new Properties("DirtPath.png"    , 0);
		types[20] = new Properties("Grass1.png"      , 0);
		types[21] = new Properties("Water1.gif"      , 0);
		types[22] = new Properties("RoyalCarpetV.png", 0);
		types[23] = new Properties("Table3.png"      , 1);
		types[24] = new Properties("ChairUp.png"     , 1);
		types[25] = new Properties("ChairDown.png"   , 1);
		types[26] = new Properties("ChairLeft.png"   , 1);
		types[27] = new Properties("ChairRight.png"  , 1);
		types[28] = new Properties("Table4.png"      , 1);
		types[29] = new Properties("Table5.png"      , 1);
		types[30] = new Properties("Table6.png"      , 1);
		types[31] = new Properties("StonebrickW1.png", 1);
		types[32] = new Properties("StonebrickW2.png", 1);
		types[33] = new Properties("StonebrickW3.png", 1);
		types[34] = new Properties("Crate1.png"      , 1);
		types[35] = new Properties("RoyalCarpetH.png", 0);
		types[36] = new Properties("RoyalCarpetC.png", 0);
		types[37] = new Properties("StoneRoad.png"   , 0);
		types[38] = new Properties("KingsThrone.png" , 1);
	}

	/**
	 * Tile constructor
	 * @param x X position
	 * @param y Y position
	 * @param type Tile ID
	 */
	public Tile(int x, int y, int type) {
		super(x, y);
		this.type = type;
		
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

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;

		if (!(obj instanceof Tile))
			return false;

		return (int)getX() == (int)((Tile)obj).getX() && (int)getY() == (int)((Tile)obj).getY()
				&& getType() == ((Tile)obj).getType();
	}

	public static boolean isDoor(int id) {
		return id == 13 || id == 16 || id == 17 || id == 18;
	}

	public static boolean isUpwardDoor(int id) {
		return id == 13;
	}

	public static boolean isDownwardDoor(int id) {
		return id == 16;
	}

	public static boolean isLeftwardDoor(int id) {
		return id == 18;
	}

	public static boolean isRightwardDoor(int id) {
		return id == 17;
	}

	public boolean isDoor() {
		return Tile.isDoor(type);
	}

	public boolean isUpwardDoor() {
		return Tile.isUpwardDoor(this.type);
	}

	public boolean isDownwardDoor() {
		return Tile.isDownwardDoor(this.type);
	}

	public boolean isLeftwardDoor() {
		return Tile.isLeftwardDoor(this.type);
	}

	public boolean isRightwardDoor() {
		return Tile.isRightwardDoor(this.type);
	}

	/**
	 * @author K
	 */
	public static boolean isLockedDoor(int id) {
		return false;
	}

	public boolean isLockedDoor() {
		return Tile.isLockedDoor(type);
	}

	public boolean emitsParticle() {
		return type == 11;
	}

	public Class<? extends Particle> getParticleClass() {
		if (type == 11)
			return ParticleFire.class;

		return null;
	}
	/**
	 * Returns the tile ID.
	 */
	public int getType() {
		return type;
	}
}
