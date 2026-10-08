package com.gildedrose;

class GildedRose {
    private Item[] items;

    private static final String AGED_BRIE = "Aged Brie";
    private static final String SULFURAS = "Sulfuras, Hand of Ragnaros";
    private static final String TAFKAL_PASSES = "Backstage passes to a TAFKAL80ETC concert";
    private static final String CONJURED_KEYWORD = "conjured";

    public GildedRose(Item[] items) {
        this.items = items;
    }

    private static boolean isConjured(Item item){
        return item.name.toLowerCase().contains(CONJURED_KEYWORD);
    }

    private static boolean isExceptionalItem(Item item){
        return item.name.equals(AGED_BRIE)
        || item.name.equals(SULFURAS)
        || item.name.equals(TAFKAL_PASSES)
        || isConjured(item);
    }

    private static void updateNormalItem(Item item){
        int degradingRate = 1;
        if(isConjured(item)){
            degradingRate = 2;
        }

        if(item.sellIn >= 0)
            item.quality-=degradingRate;
        else
            item.quality-=(degradingRate*2);

        if(item.quality < 0){
            item.quality = 0;
        }

        item.sellIn--;
    }

    private static void updateAgedBrie(Item item){
        if(item.sellIn >= 0)
            item.quality++;
        else
            item.quality+=2;

        if(item.quality > 50) item.quality = 50;

        item.sellIn--;
    }

    private static void updateSulfuras(Item item){
        item.quality = 80;
    }

    private static void updateTafkalPasses(Item item){
        if(item.sellIn > 0){
            if(item.sellIn < 6){
                item.quality+=3;
            }else if(item.sellIn < 11){
                item.quality+=2;
            }else{
                item.quality++;
            }

            if(item.quality >= 50){
                item.quality = 50;
            }
        }else{
            item.quality = 0;
        }

        item.sellIn--;
    }

    private static void updateConjuredItem(Item item){
        GildedRose.updateNormalItem(item);
    }

    public static void updateExceptionalItem(Item item){
        if(item.name.equals(AGED_BRIE)){
            updateAgedBrie(item);
        }else if(item.name.equals(SULFURAS)){
            updateSulfuras(item);
        }else if(item.name.equals(TAFKAL_PASSES)){
            updateTafkalPasses(item);
        }else if(isConjured(item)){
            updateConjuredItem(item);
        }
    }

    public void updateQuality() {
        for(int i = 0; i < items.length; i++){
            if(isExceptionalItem(items[i])){
                updateExceptionalItem(items[i]);
            }else{
                updateNormalItem(items[i]);
            }
        }
    }
}
