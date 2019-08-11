/*
 * This Plugin was written by depascaldc ( Discord: Pascal#1234 )
 *  
 * Copyright © 2019 | depascaldc - Management | All Rights Reserved
 * Unauthorized copying, distributing, and using of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Pascal Neiße (depascaldc) <support@depascaldc.de> https://discord.gg/dqRMdEk
 *  
 */
package de.depascaldc.nukkitxprojects.luckyblocks;

import java.util.Random;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.block.BlockAir;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.item.EntityPrimedTNT;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.item.Item;
import cn.nukkit.level.Level;
import cn.nukkit.level.Location;
import cn.nukkit.level.Sound;
import cn.nukkit.math.NukkitRandom;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.nbt.tag.DoubleTag;
import cn.nukkit.nbt.tag.FloatTag;
import cn.nukkit.nbt.tag.ListTag;
import cn.nukkit.potion.Effect;
import nukkitcoders.mobplugin.MobPlugin;

public class BlockDestroy implements Listener {

	public void onEvent(BlockBreakEvent e) {
		Block b = e.getBlock();
		if (b.getId() == 19) {

			boolean mobplugin = true;
			if (e.getPlayer().getServer().getPluginManager().getPlugin("MobPlugin") == null)
				mobplugin = false;

			int action = 1;
			if (mobplugin)
				action = getRandomInt(0, 13);
			if (!mobplugin)
				action = getRandomInt(0, 11);
			
			Player p = e.getPlayer();
			Location ploc = p.getLocation();
			Location bloc = b.getLocation();
			Level world = p.getLevel();
			int notUse = 0;
			int height = 0;
			int X = (int) (ploc.getX() + 1);
			int Y = (int) ploc.getY();
			int Z = (int) (ploc.getZ() - 1);
			int bX = (int) (bloc.getX() + 1);
			int bY = (int) bloc.getY();
			int bZ = (int) (bloc.getZ() - 1);
			
			Effect slowness = Effect.getEffect(Effect.MINING_FATIGUE);
			Effect drunken = Effect.getEffect(Effect.CONFUSION);

			switch (action) {
			// EXP drop
			case 0: {
				world.setBlockAt((int) bloc.getX(), (int) bloc.getY(), (int) bloc.getZ(), Block.AIR, 0);
				world.dropExpOrb(bloc, 20);
				world.dropExpOrb(bloc, 20);
				world.dropExpOrb(bloc, 20);
				world.dropExpOrb(bloc, 20);
				world.dropExpOrb(bloc, 20);
				world.dropExpOrb(bloc, 20);
				e.setCancelled(true);
				break;
			}
			// New Luckyblock drop
			case 1: {
				e.setCancelled(true);
				break;
			}
			// TNT spawn 9er radius
			case 2: {
				for (int j = bX; j > bX - 5; --j) {
					for (int k = bZ; k < bZ + 5; ++k) {
						for (int l = bY + 2; l < bY + 3; ++l) {
							if (notUse != 0 && notUse != 2 && notUse != 4 && notUse != 10 && notUse != 12
									&& notUse != 14 && notUse != 20 && notUse != 22 && notUse != 24)
								continue;
							world.setBlockAt(j, l, k, 46, 0);
							Block tnt2 = world.getBlock(j, l, k);
							igniteTNT(tnt2, p);
						}
						++notUse;
					}
				}
				world.setBlockAt((int) bloc.getX(), (int) bloc.getY(), (int) bloc.getZ(), BlockAir.AIR, 0);
				e.setCancelled(true);
				break;
			}
			// lava case with exploding tnt
			case 3: {
				for (int j = X; j > X - 3; --j) {
					for (int k = Z; k < Z + 3; ++k) {
						for (int l = Y; l < Y + 4; ++l) {
							if (notUse != 4) {
								world.setBlockAt(j, l, k, 101, 0);
								continue;
							}
							world.setBlockAt(j, l, k, BlockAir.AIR, 0);
						}
						++notUse;
					}
				}
				world.setBlockAt((int) ploc.getX(), (int) (ploc.getY() + 3), (int) ploc.getZ(), 10, 0);
				world.setBlockAt((int) ploc.getX(), (int) (ploc.getY() + 4), (int) ploc.getZ(), 46, 0);
				Block tnt = world.getBlock((int) ploc.getX(), (int) (ploc.getY() + 4), (int) ploc.getZ());
				igniteTNT(tnt, p);
				world.setBlockAt((int) bloc.getX(), (int) bloc.getY(), (int) bloc.getZ(), BlockAir.AIR, 0);
				e.setCancelled(true);
				break;
			}
			// obsidian water prison
			case 4: {
				for (int j = X; j > X - 3; --j) {
					for (int k = Z; k < Z + 3; ++k) {
						for (int l = Y - 1; l < Y + 3; ++l) {
							if (notUse != 4 && height != 0 && height != 3 && height != 2) {
								world.setBlockAt(j, l, k, Block.OBSIDIAN, 0);
							} else if (height == 2 && (notUse == 0 || notUse == 2 || notUse == 6 || notUse == 8)) {
								world.setBlockAt(j, l, k, Block.OBSIDIAN, 0);
							} else if (height == 0 || height == 3) {
								world.setBlockAt(j, l, k, Block.OBSIDIAN, 0);
							} else if (height == 2 && (notUse == 1 || notUse == 3 || notUse == 5 || notUse == 7)) {
								world.setBlockAt(j, l, k, Block.GLASS, 0);
							} else {
								world.setBlockAt(j, l, k, 9, 0);
							}
							++height;
						}
						height = 0;
						++notUse;
					}
				}
				world.setBlockAt((int) bloc.getX(), (int) bloc.getY(), (int) bloc.getZ(), BlockAir.AIR, 0);
				e.setCancelled(true);
				break;
			}
			// PYRAMIDE ( sandstone / gold / 4 Luckyblocks
			case 5 : {
				for (int j = X; j < X + 7; ++j) {
					for (int k = Z; k > Z - 7; --k) {
						for (int l = Y; l < Y + 4; ++l) {
							if (height == 0 && (notUse >= 0 && notUse <= 6 || notUse == 7 || notUse == 13
									|| notUse == 14 || notUse == 20 || notUse == 21 || notUse == 27 || notUse == 28
									|| notUse == 34 || notUse == 35 || notUse == 41 || notUse >= 42 && notUse <= 48)) {
								world.setBlockAt(j, l, k, 24, 0);
							} else if (height == 1 && (notUse >= 8 && notUse <= 12 || notUse >= 36 && notUse <= 40
									|| notUse == 15 || notUse == 22 || notUse == 29 || notUse == 19 || notUse == 26
									|| notUse == 33)) {
								world.setBlockAt(j, l, k, 24, 0);
							} else if (height == 2 && (notUse >= 16 && notUse <= 18 || notUse >= 30 && notUse <= 32
									|| notUse == 23 || notUse == 25)) {
								world.setBlockAt(j, l, k, 24, 0);
							} else if (height == 3 && notUse == 24) {
								world.setBlockAt(j, l, k, 24, 0);
							}
							if (height >= 0 && height <= 2
									&& (notUse == 0 || notUse == 6 || notUse == 42 || notUse == 48)) {
								world.setBlockAt(j, l, k, 24, 0);
							}
							if (height == 3 && (notUse == 0 || notUse == 6 || notUse == 42 || notUse == 48)) {
								world.setBlockAt(j, l, k, 41, 0);
							}
							if (height == 0 && (notUse == 8 || notUse == 12 || notUse == 36 || notUse == 40)) {
								world.setBlockAt(j, l, k, 19, 0);
							}
							++height;
						}
						height = 0;
						++notUse;
					}
				}
				world.setBlockAt((int) bloc.getX(), (int) bloc.getY(), (int) bloc.getZ(), BlockAir.AIR, 0);
				e.setCancelled(true);
				break;
			}
			// teleport player to spawn
			case 6: {
				p.teleport(p.getLevel().getSafeSpawn());
				world.setBlockAt((int) bloc.getX(), (int) bloc.getY(), (int) bloc.getZ(), BlockAir.AIR, 0);
				e.setCancelled(true);
				break;
			}
			// teleport player one down
			case 7: {
				Location oneDown = new Location(X, Y - 1, Z, p.getYaw(), p.getPitch());
				p.teleport(oneDown);
				world.setBlockAt((int) bloc.getX(), (int) bloc.getY(), (int) bloc.getZ(), BlockAir.AIR, 0);
				e.setCancelled(true);
				break;
			}
			// add player mining fatigue 5 min 
			case 8: {
				slowness.setDuration(20 * 60 * 5);
				slowness.setAmplifier(3);
				p.addEffect(slowness);
				world.setBlockAt((int) bloc.getX(), (int) bloc.getY(), (int) bloc.getZ(), BlockAir.AIR, 0);
				e.setCancelled(true);
				break;
			}
			// make player drunken 
			case 9: {
				drunken.setDuration(20 * 60 * 5);
				p.addEffect(drunken);
				world.setBlockAt((int) bloc.getX(), (int) bloc.getY(), (int) bloc.getZ(), BlockAir.AIR, 0);
				e.setCancelled(true);
				break;
			}
			// clear item in hand
			case 10: {
				Item air = new Item(0);
				p.getInventory().setItemInHand(air);
				world.setBlockAt((int) bloc.getX(), (int) bloc.getY(), (int) bloc.getZ(), BlockAir.AIR, 0);
				e.setCancelled(true);
				break;
			}
			// destroy ground under player ( 9x9 ) ( nobedrock)
			case 11: {
				for (int j = X; j > X - 3; --j) {
					for (int k = Z; k < Z + 3; ++k) {
						for (int l = 0; l < Y + 1; ++l) {
							Block btc = world.getBlock(j, l, k);
							if (btc.getId() == 7)
								continue;
							world.setBlockAt(j, l, k, BlockAir.AIR);
						}
					}
				}
				world.setBlockAt((int) bloc.getX(), (int) bloc.getY(), (int) bloc.getZ(), BlockAir.AIR, 0);
				e.setCancelled(true);
				break;
			}
			
			/**
			 * TODO add possibility for serverowners to add Itemdrops
			 */
			
			
			// if mobplugin enabled
			// spawn mob zombie/creeper/vex to annoy player
			case 12 : {
				Entity ent = MobPlugin.create("Zombie", bloc, new Object[0]);
				ent = MobPlugin.create("Zombie", bloc, new Object[0]);
				if (ent != null) {
					ent.spawnToAll();
				}
				ent = MobPlugin.create("Creeper", bloc, new Object[0]);
				if (ent != null) {
					ent.spawnToAll();
				}
				ent = MobPlugin.create("Vex", bloc, new Object[0]);
				if (ent != null) {
					ent.spawnToAll();
				}
				world.setBlockAt((int) bloc.getX(), (int) bloc.getY(), (int) bloc.getZ(), BlockAir.AIR, 0);
				e.setCancelled(true);
				break;
			}
			// spawn random 1 - 4 entities of array entitytype
			case 13: {
				String[] entitytype = new String[] { "Cow", "Pig", "Horse", "Mushroom", "Blaze", "Cavespider",
						"Creeper", "Enderman", "Ghast", "Zombiepigman", "Silverfish", "Slime", "Witch", "Zombie" };
				int Randomizer = (int) (Math.random() * (double) entitytype.length);
				int count = getRandomInt(1, 3);
				for (int i = 0; i < count; ++i) {
					MobPlugin.create((String) entitytype[Randomizer], bloc, new Object[0]);
				}
				world.setBlockAt((int) bloc.getX(), (int) bloc.getY(), (int) bloc.getZ(), BlockAir.AIR, 0);
				e.setCancelled(true);
				break;
			}
			}

		}

	}

	private static int getRandomInt(int min, int max) {
		if (min >= max) {
			throw new IllegalArgumentException("max muss grösser sein als min");
		}
		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}

	private static void igniteTNT(Block bl, Player p) {
		bl.getLevel().setBlock(bl, new BlockAir(), true);
		double mot = (double) new NukkitRandom().nextSignedFloat() * 3.141592653589793 * 2.0;
		CompoundTag nbt = new CompoundTag()
				.putList(new ListTag<DoubleTag>("Pos").add(new DoubleTag("", bl.x + 0.5)).add(new DoubleTag("", bl.y))
						.add(new DoubleTag("", bl.z + 0.5)))
				.putList(new ListTag<DoubleTag>("Motion").add(new DoubleTag("", (-Math.sin(mot)) * 0.02))
						.add(new DoubleTag("", 0.2)).add(new DoubleTag("", (-Math.cos(mot)) * 0.02)))
				.putList(new ListTag<FloatTag>("Rotation").add(new FloatTag("", 0.0f)).add(new FloatTag("", 0.0f)))
				.putShort("Fuse", 80);
		EntityPrimedTNT tnt = new EntityPrimedTNT(bl.getLevel().getChunk(bl.getFloorX() >> 4, bl.getFloorZ() >> 4), nbt,
				p);
		tnt.spawnToAll();
		bl.level.addSound(bl, Sound.RANDOM_FUSE);
	}

}
