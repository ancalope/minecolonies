package com.minecolonies.commands;

import com.minecolonies.colony.CitizenData;
import com.minecolonies.colony.Colony;
import com.minecolonies.colony.ColonyManager;
import com.minecolonies.entity.EntityCitizen;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * List all colonies.
 */
public class RespawnCitizen extends AbstractSingleCommand
{

    private static final String ID_TEXT                         = "§2ID: §f";
    private static final String NAME_TEXT                       = "§2 Name: §f";
    private static final String COORDINATES_TEXT                = "§2Coordinates: §f";
    private static final String REMOVED_MESSAGE                 = "Has been removed";
    private static final String NO_COLONY_CITIZEN_FOUND_MESSAGE = "No citizen %d found in colony %d.";
    private static final String COORDINATES_XYZ                 = "§4x=§f%s §4y=§f%s §4z=§f%s";
    private static final String CITIZEN_DATA_NULL               = "Couldn't find citizen client side representation of %d in %d";
    private static final String ENTITY_CITIZEN_NULL             = "Couldn't find entity of %d in %d";

    public static final String DESC                             = "respawn";

    /**
     * Initialize this SubCommand with it's parents.
     *
     * @param parents an array of all the parents.
     */
    public RespawnCitizen(@NotNull final String... parents)
    {
        super(parents);
    }

    @NotNull
    @Override
    public String getCommandUsage(@NotNull final ICommandSender sender)
    {
        return super.getCommandUsage(sender) + "";
    }

    @Override
    public void execute(@NotNull final MinecraftServer server, @NotNull final ICommandSender sender, @NotNull final String... args) throws CommandException
    {
        final int colonyId = getIthArgument(args, 0, -1);
        final int citizenId = getIthArgument(args, 1, -1);

        //todo add this in a feature update when we added argument parsing and permission handling.
        /*if(colonyId == -1)
        {
            colonyId = getColonyId(sender);
        }*/

        //No citizen or citizen defined.
        if(colonyId == -1 || citizenId == -1)
        {
            sender.addChatMessage(new TextComponentString(String.format(NO_COLONY_CITIZEN_FOUND_MESSAGE, citizenId, colonyId)));
            return;
        }

        //Wasn't able to get the citizen from the colony.
        final Colony colony = ColonyManager.getColony(colonyId);
        final CitizenData citizenData = colony.getCitizen(citizenId);
        if(citizenData == null)
        {
            sender.addChatMessage(new TextComponentString(String.format(CITIZEN_DATA_NULL, citizenId, colonyId)));
            return;
        }

        //Wasn't able to get the entity from the citizenData.
        final EntityCitizen entityCitizen = citizenData.getCitizenEntity();
        if(entityCitizen == null)
        {
            sender.addChatMessage(new TextComponentString(String.format(ENTITY_CITIZEN_NULL, citizenId, colonyId)));
            return;
        }

        sender.addChatMessage(new TextComponentString(ID_TEXT + citizenData.getId() + NAME_TEXT + citizenData.getName()));
        final BlockPos position = entityCitizen.getPosition();
        sender.addChatMessage(new TextComponentString(COORDINATES_TEXT + String.format(COORDINATES_XYZ, position.getX(), position.getY(), position.getZ())));
        sender.addChatMessage(new TextComponentString(REMOVED_MESSAGE));

        entityCitizen.setDead();
    }

    @NotNull
    @Override
    public List<String> getTabCompletionOptions(
                                                 @NotNull final MinecraftServer server,
                                                 @NotNull final ICommandSender sender,
                                                 @NotNull final String[] args,
                                                 @Nullable final BlockPos pos)
    {
        return new ArrayList<>();
    }

    @Override
    public boolean isUsernameIndex(@NotNull final String[] args, final int index)
    {
        return false;
    }
}
