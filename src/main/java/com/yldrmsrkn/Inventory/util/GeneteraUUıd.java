package com.yldrmsrkn.Inventory.util;

import java.util.Random;
import java.util.UUID;

public class GeneteraUUıd {
    public static UUID generateCode() {
        Random rand = new Random();
        int codeFirstValue = rand.nextInt(99999999);
        int codeSecondValue = rand.nextInt(999);

        return UUID.randomUUID();
    }
}
