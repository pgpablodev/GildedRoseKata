# Gilded Rose Refactoring Kata

For this refactoring exercise, I’m going to identify and document the different code smells I detected, as well as the refactorings I applied and the difficulties I encountered.

## Code Smells and Refactorings

### 1. Long Method and Deeply Nested Conditionals

The original `updateQuality()` method contained all the business logic for every type of item. It had several levels of nested `if` statements, making it difficult to understand and modify.

I extracted the logic into separate methods for the different item behaviours, such as `updateNormalItem()`, `updateAgedBrie()`, `updateSulfuras()` and `updateTafkalPasses()`. This reduced the complexity of `updateQuality()` and made each method responsible for a specific behaviour.

### 2. Repeated Conditional Logic

The original `updateQuality()` repeatedly checked item names to determine how each item should behave. This made the control flow difficult to follow and mixed item classification with item updates.

I extracted `isExceptionalItem()` to centralize the identification of items with special behaviour. `updateQuality()` can now simply decide whether an item requires normal or exceptional handling.

### 3. Hard-coded Strings

Item names such as `"Aged Brie"`, `"Sulfuras, Hand of Ragnaros"` and `"Backstage passes to a TAFKAL80ETC concert"` were repeated throughout the code.

I extracted these values into constants, making the code easier to read and reducing the risk of inconsistencies when referring to the same item type.

### 4. Conjured Items Mixed with Normal Item Logic

The new Conjured requirement introduced another special case into the existing conditional structure. I extracted `isConjured()` to centralize the identification of Conjured items and reused the normal item update logic with a different degradation rate.

### 5. Complex Backstage Pass Logic

The original Backstage Pass logic contained nested conditions for the different time periods before the concert. I separated it into `updateTafkalPasses()` and made the three quality increase rules explicit: +1, +2 and +3 depending on the remaining `SellIn` value.

During this refactoring I also found a bug in the conditional order. The condition for less than 11 days was being evaluated before the condition for less than 6 days, making the +3 case unreachable. I corrected the order and added tests for the boundary values.

### 6. Magic Numbers

The original code contained several unexplained numeric values such as 50, 80, 11 and 6 directly inside the business logic.

I did not extract all of these into constants because, in this case, the values are directly related to the domain rules and remain reasonably clear in context. The tests document the important boundary conditions.

## Testing

The original test suite only contained a placeholder test that did not describe any real Gilded Rose behaviour. I replaced it with tests covering the different item types and their relevant edge cases.

The tests cover normal items, Aged Brie, Sulfuras, Backstage Passes and Conjured items, including expiration, Quality limits and the Backstage Pass boundary conditions.

One difficulty was that some tests initially passed even when the implementation contained an incorrect Backstage Pass condition. The tests were using a starting Quality of 49, so both the incorrect and correct implementations reached the maximum Quality of 50. I changed the test values so that the tests specifically verify the expected +2 and +3 increases.

## Final Result

The main improvement was to turn `updateQuality()` from a large, deeply nested method into a simple coordinator:

```java
if (isExceptionalItem(items[i])) {
    updateExceptionalItem(items[i]);
} else {
    updateNormalItem(items[i]);
}
```

The behaviour is now divided into smaller, focused methods, the new Conjured functionality is covered by tests, and the original `Item` class remains unchanged as required by the kata.
