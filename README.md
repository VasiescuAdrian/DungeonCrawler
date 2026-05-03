# 🏰 Dungeon Crawler RPG (Java)

A text-based dungeon crawler RPG built in Java featuring turn-based combat, skills, status effects, merchants, consumables, and procedural dungeon generation with bosses and events.

---

## Features

### Turn-Based Combat System
- Player vs enemy turn-based fights
- Attack, skills, defend, use items, or escape
- Stun system (skip turns when stunned)
- Damage modifiers (buffs, debuffs, defense scaling)

### Skill System
- Multiple skill types (damage, heal, debuffs, crowd control)
- Mana-based abilities
- Conditional effects (execute, poison, mark, etc.)
- Enemy AI can also use skills

### Status Effects
- Poison (damage over time)
- Burn
- Stun (skip turn)
- Weakness / Enrage / Shielded / Marked / Defending
- Duration-based effect system

### Enemies & Bosses
- Different enemy types with scaling stats
- Bosses with phase transitions (Phase 1 → Phase 2 enraged state)
- Enemy skill selection logic (basic AI behavior)

### Merchant System
- Upgrade weapon damage
- Upgrade armor defense
- Buy consumables:
  - Health potions
  - Mana potions

### Inventory System
- Consumable items (health/mana potions)
- Item usage during combat

### Dungeon Generation
- Procedurally generated floors
- Room types:
  - Combat Rooms
  - Event Rooms
  - Merchant Rooms
  - Boss Rooms
- Randomized encounters and loot

### Event System
- Special rooms like:
  - Abandoned Altar
  - Chest Rooms
- Risk/reward choices with random outcomes

---

## How it works

1. Player creates a character and selects a class
2. Dungeon floors are generated with random rooms
3. Player progresses room by room:
   - Combat encounters
   - Events
   - Merchants
   - Boss fights
4. Enemies scale with floor level
5. Defeating enemies gives XP and gold
6. Merchant allows upgrades and item purchases

---

## Core Systems

- CombatManager handles all fight logic
- SkillHandler executes abilities and effects
- DungeonGenerator builds floors dynamically
- GameController manages game flow
- StatusEffect system controls buffs/debuffs

---

##  Future Improvements (Ideas)
- Equipment system (weapons/armor with rarity)
- More enemy types (Elite/Corrupted) and more bosses
- More Item variation
- Save/Load system
- Better AI decision-making
- UI upgrade (JavaFX or Swing)

---

## Author

Built as a learning project to explore:
- OOP design in Java
- Game loop architecture
- State management
- Combat systems in RPGs

---

## Note

This is a console-based RPG project focused on backend/game logic rather than graphics. It was a really tough journey since it is my first game ever created, and I learned everything on the go, stayed on this project for some time.
