package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.EmailMatchesPredicate;
import seedu.address.model.person.GroupMatchesPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.NusIdMatchesPredicate;
import seedu.address.model.person.PhoneMatchesPredicate;
import seedu.address.model.person.TagMatchesPredicate;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";
    // This string is used by Predicates to help them know when a parameter isn't required.
    public static final String NOT_REQUIRED_VALUE = "$$NOT_REQUIRED$$";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all people who matches ALL of "
            + "the specified keywords and displays them as a list.\n"
            + "Usage: find [id/NUSID] [n/NAME] [e/EMAIL] [p/PHONE_NUMBER] [t/TAG] [g/GROUP]...\n"
            + "Example: " + COMMAND_WORD + " n/Peter Parker " + "g/CS2101";

    private final NusIdMatchesPredicate nusIdPredicate;
    private final NameContainsKeywordsPredicate namePredicate;
    private final EmailMatchesPredicate emailPredicate;
    private final GroupMatchesPredicate groupPredicate;
    private final PhoneMatchesPredicate phonePredicate;
    private final TagMatchesPredicate tagPredicate;

    /**
     * Creates a FindCommand with predicates initialized with the provided predicates
     *
     * @param id nusId
     * @param n name
     * @param e email
     * @param g group
     * @param p phone
     * @param t tag
     */

    public FindCommand(
                       NusIdMatchesPredicate id,
                       NameContainsKeywordsPredicate n,
                       EmailMatchesPredicate e,
                       GroupMatchesPredicate g,
                       PhoneMatchesPredicate p,
                       TagMatchesPredicate t) {
        nusIdPredicate = id;
        namePredicate = n;
        emailPredicate = e;
        groupPredicate = g;
        phonePredicate = p;
        tagPredicate = t;

    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        model.updateFilteredPersonList(nusIdPredicate
                .and(namePredicate)
                .and(emailPredicate)
                .and(groupPredicate)
                .and(phonePredicate)
                .and(tagPredicate));
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindCommand)) {
            return false;
        }

        FindCommand otherFindCommand = (FindCommand) other;
        boolean a = nusIdPredicate.equals(otherFindCommand.nusIdPredicate);
        boolean b = namePredicate.equals(otherFindCommand.namePredicate);
        boolean c = emailPredicate.equals(otherFindCommand.emailPredicate);
        boolean d = groupPredicate.equals(otherFindCommand.groupPredicate);
        boolean e = phonePredicate.equals(otherFindCommand.phonePredicate);
        boolean f = tagPredicate.equals(otherFindCommand.tagPredicate);
        return a && b && c && d && e && f;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("nusIdPredicate", nusIdPredicate)
                .add("namePredicate", namePredicate)
                .add("phonePredicate", phonePredicate)
                .add("emailPredicate", emailPredicate)
                .add("tagPredicate", tagPredicate)
                .add("groupPredicate", groupPredicate)
                .toString();
    }
}
