package com.github.leonidstein.data;

public class FakeData {

    protected static final String ALPHABET = "AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz";
    protected static final String NUMERIC_CHARS = "0123456789";
    protected static final String SPECIAL_CHARS = "!@#$%^&*()?',.";
    protected static final String RANDOM_STRING = ALPHABET + NUMERIC_CHARS + SPECIAL_CHARS;

    public static final int INVALID_PASSWORD_LENGTH = 256;
    public static final int INVALID_LOGIN_LENGTH = 256;

    public static final double MIN_PRICE = 1;
    public static final double MAX_PRICE = 1_000;
    public static final double MIN_PRICE_FOR_UPDATE = 1_001;
    public static final double MAX_PRICE_FOR_UPDATE = 10_000;

    public static int MIN_RATING = 1;
    public static int MAX_RATING = 100;
    public static int MIN_RATING_FOR_UPDATE = 101;
    public static int MAX_RATING_FOR_UPDATE = 1_000;

    public static final String[] DLC_ARRAY = new String[]{
            "BioShock 2: Minerva's Den",
            "The Witcher 3: Wild Hunt - Hearts of Stone",
            "The Elder Scrolls V: Skyrim - Dawnguard",
            "Cyberpunk 2077: Phantom Liberty",
            "Metro: Exodus - The Two Colonels"
    };

    public static final String[] DESCRIPTION_ARRAY = new String[]{
            "A fantastic first-person shooter with RPG elements, a sequel to the BioShock game",
            "This is the third game in the literary universe of the Witcher book series",
            "An open-world action/RPG computer game, the fifth installment in the Elder Scrolls series",
            "An open-world Action/RPG computer game developed by the Polish studio CD Projekt RED",
            "This is the third game in the Metro series, continuing the story of Metro 2033 and Metro: Last Light"
    };

    public static final String[] COMPANY_ARRAY = new String[]{
            "Ubisoft",
            "CD Projekt RED",
            "Electronic Arts (EA)",
            "Activision Blizzard",
            "Square Enix"
    };

    public static final int[] HARD_DRIVE_ARRAY = new int[]{
            50, 100, 150, 200, 250
    };

    public static final String[] OS_NAME_ARRAY = new String[]{
            "Windows 7",
            "Windows 10",
            "Windows 11",
            "MacOS",
            "Linux"
    };

    public static final int[] RAM_GB_ARRAY = new int[]{
            2, 4, 8, 16, 32
    };

    public static final String[] VIDEO_CARD_ARRAY = new String[]{
            "NVIDIA GeForce RTX 5090",
            "NVIDIA TITAN Xp",
            "AMD Radeon RX 7800 XT",
            "Intel Arc A750",
            "AMD Radeon RX 5600 XT",
            "NVIDIA GeForce GTX 1060 6GB"
    };

    public static final String[] GENRE_ARRAY = new String[]{
            "The Shooter",
            "Horror",
            "Simulator",
            "RPG",
            "Interactive cinema"
    };

    public static final String[] TAG_ARRAY = new String[]{
            "Single player game",
            "Online game",
            "Multiplayer mode",
            "Offline game"
    };

    public static final String[] TITLE_ARRAY = new String[]{
            "BioShock 2",
            "The Witcher 3",
            "The Elder Scrolls V",
            "Cyberpunk 2077",
            "Metro: Exodus"
    };
}
