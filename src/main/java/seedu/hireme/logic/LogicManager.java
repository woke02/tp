package seedu.hireme.logic;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.hireme.commons.core.GuiSettings;
import seedu.hireme.commons.core.LogsCenter;
import seedu.hireme.logic.commands.Command;
import seedu.hireme.logic.commands.CommandResult;
import seedu.hireme.logic.commands.exceptions.CommandException;
import seedu.hireme.logic.parser.AddressBookParser;
import seedu.hireme.logic.parser.exceptions.ParseException;
import seedu.hireme.model.Model;
import seedu.hireme.model.ReadOnlyAddressBook;
import seedu.hireme.model.internshipapplication.InternshipApplication;
import seedu.hireme.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic<InternshipApplication> {
    public static final String FILE_OPS_ERROR_FORMAT = "Could not save data due to the following error: %s";

    public static final String FILE_OPS_PERMISSION_ERROR_FORMAT =
            "Could not save data to file %s due to insufficient permissions to write to the file or the folder.";

    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model<InternshipApplication> model;
    private final Storage<InternshipApplication> storage;
    private final AddressBookParser addressBookParser;

    /**
     * Constructs a {@code LogicManager} with the given {@code Model} and {@code Storage}.
     */
    public LogicManager(Model<InternshipApplication> model, Storage<InternshipApplication> storage) {
        this.model = model;
        this.storage = storage;
        addressBookParser = new AddressBookParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command<InternshipApplication> command = addressBookParser.parseCommand(commandText);
        commandResult = command.execute(model);

        try {
            storage.saveAddressBook(model.getAddressBook());
        } catch (AccessDeniedException e) {
            throw new CommandException(String.format(FILE_OPS_PERMISSION_ERROR_FORMAT, e.getMessage()), e);
        } catch (IOException ioe) {
            throw new CommandException(String.format(FILE_OPS_ERROR_FORMAT, ioe.getMessage()), ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyAddressBook<InternshipApplication> getAddressBook() {
        return model.getAddressBook();
    }

    @Override
    public ObservableList<InternshipApplication> getFilteredList() {
        return model.getFilteredList();
    }

    @Override
    public Path getAddressBookFilePath() {
        return model.getHireMeFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }
}
