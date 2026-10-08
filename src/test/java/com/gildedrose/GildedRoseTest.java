package com.gildedrose;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;


public class GildedRoseTest {
    private Item[] items;

    @Before
    public void setUp() {
        this.items = new Item[] {
            new Item("+5 Dexterity Vest", 10, 20),
            new Item("Aged Brie", 2, 0),
            new Item("Elixir of the Mongoose", 5, 7),
            new Item("Sulfuras, Hand of Ragnaros", 0, 80),
            new Item("Sulfuras, Hand of Ragnaros", -1, 80),
            new Item("Backstage passes to a TAFKAL80ETC concert", 15, 20),
            new Item("Backstage passes to a TAFKAL80ETC concert", 10, 49),
            new Item("Backstage passes to a TAFKAL80ETC concert", 5, 49),
            new Item("Conjured Mana Cake", 3, 6)
        };
    }

    @Test
    public void shouldDecreaseQualityOfNormalItem() {
        GildedRose app = new GildedRose(this.items);

        for (int i = 0; i < 8; i++) {
            app.updateQuality();
        }

        assertEquals(12, this.items[0].quality);
        assertEquals(2, this.items[0].sellIn);
    }

    @Test
    public void shouldDecreaseElixirQuality() {
        GildedRose app = new GildedRose(this.items);

        app.updateQuality();

        assertEquals(6, this.items[2].quality);
        assertEquals(4, this.items[2].sellIn);
    }

    @Test
    public void shouldDecreaseQualityTwiceAfterSellIn() {
        this.items[2].sellIn = -1;

        GildedRose app = new GildedRose(this.items);

        app.updateQuality();

        assertEquals(5, this.items[2].quality);
        assertEquals(-2, this.items[2].sellIn);
    }

    @Test
    public void agedBrieIncreasesQuality() {
        GildedRose app = new GildedRose(this.items);

        app.updateQuality();

        assertEquals(1, this.items[1].quality);
        assertEquals(1, this.items[1].sellIn);
    }

    @Test
    public void agedBrieQualityCannotExceed50() {
        this.items[1].quality = 50;

        GildedRose app = new GildedRose(this.items);

        app.updateQuality();

        assertEquals(50, this.items[1].quality);
    }

    @Test
    public void sulfurasDoesNotChange() {
        GildedRose app = new GildedRose(this.items);

        app.updateQuality();

        assertEquals(80, this.items[3].quality);
        assertEquals(0, this.items[3].sellIn);
    }

    @Test
    public void backstagePassIncreasesQualityByOne() {
        GildedRose app = new GildedRose(this.items);

        app.updateQuality();

        assertEquals(21, this.items[5].quality);
        assertEquals(14, this.items[5].sellIn);
    }

    @Test
    public void backstagePassIncreasesQualityByTwoWhenTenDaysOrLess() {
        this.items[6].quality = 40;

        GildedRose app = new GildedRose(this.items);

        app.updateQuality();

        assertEquals(42, this.items[6].quality);
        assertEquals(9, this.items[6].sellIn);
    }

    @Test
    public void backstagePassIncreasesQualityByThreeWhenFiveDaysOrLess() {
        this.items[7].quality = 40;

        GildedRose app = new GildedRose(this.items);

        app.updateQuality();

        assertEquals(43, this.items[7].quality);
        assertEquals(4, this.items[7].sellIn);
    }

    @Test
    public void backstagePassQualityCannotExceed50() {
        this.items[5].sellIn = 5;
        this.items[5].quality = 49;

        GildedRose app = new GildedRose(this.items);

        app.updateQuality();

        assertEquals(50, this.items[5].quality);
    }

    @Test
    public void backstagePassQualityDropsToZeroAfterConcert() {
        this.items[5].sellIn = 0;
        this.items[5].quality = 20;

        GildedRose app = new GildedRose(this.items);

        app.updateQuality();

        assertEquals(0, this.items[5].quality);
        assertEquals(-1, this.items[5].sellIn);
    }

    @Test
    public void conjuredItemDecreasesQualityByTwo() {
        GildedRose app = new GildedRose(this.items);

        app.updateQuality();

        assertEquals(4, this.items[8].quality);
        assertEquals(2, this.items[8].sellIn);
    }

    @Test
    public void conjuredItemQualityCannotGoBelowZero() {
        this.items[8].quality = 1;

        GildedRose app = new GildedRose(this.items);

        app.updateQuality();

        assertEquals(0, this.items[8].quality);
    }

    @Test
    public void backstagePassAtElevenDaysIncreasesQualityByOne() {
        this.items[5].sellIn = 11;
        this.items[5].quality = 20;

        GildedRose app = new GildedRose(this.items);
        app.updateQuality();

        assertEquals(21, this.items[5].quality);
        assertEquals(10, this.items[5].sellIn);
    }

    @Test
    public void backstagePassAtSixDaysIncreasesQualityByTwo() {
        this.items[5].sellIn = 6;
        this.items[5].quality = 20;

        GildedRose app = new GildedRose(this.items);
        app.updateQuality();

        assertEquals(22, this.items[5].quality);
        assertEquals(5, this.items[5].sellIn);
    }

    @Test
    public void agedBrieIncreasesQualityTwiceAfterSellIn() {
        this.items[1].sellIn = -1;
        this.items[1].quality = 10;

        GildedRose app = new GildedRose(this.items);
        app.updateQuality();

        assertEquals(12, this.items[1].quality);
        assertEquals(-2, this.items[1].sellIn);
    }

    @Test
    public void expiredConjuredItemDecreasesQualityByFour() {
        this.items[8].sellIn = -1;
        this.items[8].quality = 10;

        GildedRose app = new GildedRose(this.items);
        app.updateQuality();

        assertEquals(6, this.items[8].quality);
        assertEquals(-2, this.items[8].sellIn);
    }
}
