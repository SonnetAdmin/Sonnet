package sonnet;

import com.microsoft.playwright.options.AriaRole;


public interface Targets {
    /**
     * Returns a Target object representing an HTML element with the specified ID.
     *
     * @param id The ID of the HTML element.
     * @return A Target object for the specified ID.
     */
    Target id(String id);

    /**
     * Returns a Target object representing an HTML element with the specified class name.
     *
     * @param classname The class name of the HTML element.
     * @return A Target object for the specified class name.
     */
    Target classname(String classname);

    /**
     * Returns a Target object representing an HTML element with the specified class names.
     *
     * @param classnames The class names of the HTML element.
     * @return A Target object for the specified class names.
     */
    Target classnames(String... classnames);

    /**
     * Returns a Target object representing an HTML element with the specified name.
     *
     * @param name The name of the HTML element.
     * @return A Target object for the specified name.
     */
    Target name(String name);

    /**
     * Returns a Target object representing an HTML element with the specified tag and text.
     *
     * @param tag  The HTML tag of the element.
     * @param text The text content of the element.
     * @return A Target object for the specified tag and text.
     */
    Target tagWithText(Enum.HTMLTag tag, String text);

    /**
     * Returns a Target object representing an HTML element with the specified tag containing the specified text.
     *
     * @param tag  The HTML tag of the element.
     * @param text The text content of the element.
     * @return A Target object for the specified tag and text.
     */
    Target tagContainsText(Enum.HTMLTag tag, String text);

    /**
     * Returns a Target object representing an HTML element with the specified prefix and suffix.
     *
     * @param prefix The prefix of the element.
     * @param suffix The suffix of the element.
     * @return A Target object for the specified prefix and suffix.
     */
    Target splice(String prefix, String suffix);

    /**
     * Returns a Target object representing an HTML element with the specified placeholder.
     *
     * @param placeholder The placeholder of the element.
     * @return A Target object for the specified placeholder.
     */
    Target placeholder(String placeholder);

    /**
     * Returns a Target object representing an HTML element with the specified link text.
     *
     * @param text The link text of the element.
     * @return A Target object for the specified link text.
     */
    Target linkText(String text);

    /**
     * Returns a Target object representing an HTML element with the specified label.
     *
     * @param label The label of the element.
     * @return A Target object for the specified label.
     */
    Target label(String label);

    /**
     * Returns a Target object representing an HTML element with the specified alt text.
     *
     * @param altText The alt text of the element.
     * @return A Target object for the specified alt text.
     */
    Target alt(String altText);

    /**
     * Returns a Target object representing an HTML element with the specified title.
     *
     * @param title The title of the element.
     * @return A Target object for the specified title.
     */
    Target title(String title);

    /**
     * Returns a Target object representing an HTML element with the specified tag.
     *
     * @param tag The HTML tag of the element.
     * @return A Target object for the specified tag.
     */
    Target tag(Enum.HTMLTag tag);

    /**
     * Returns a Target object representing a button element with the specified text.
     *
     * @param button The text of the button.
     * @return A Target object for the specified button.
     */
    Target button(String button);

    /**
     * Returns a Target object representing a link element with the specified text.
     *
     * @param link The text of the link.
     * @return A Target object for the specified link.
     */
    Target link(String link);

    /**
     * Returns a Target object representing an HTML element with the specified ARIA role.
     *
     * @param ariaRole The ARIA role of the element.
     * @return A Target object for the specified ARIA role.
     */
    Target ariaRole(AriaRole ariaRole);

    /**
     * Returns a Target object representing an HTML element with the specified ARIA role and label.
     *
     * @param ariaRole The ARIA role of the element.
     * @param label    The label of the element.
     * @return A Target object for the specified ARIA role and label.
     */
    Target ariaRole(AriaRole ariaRole, String label);

    /**
     * Returns a Target object representing an HTML element with the specified attribute and value.
     *
     * @param attribute The HTML attribute of the element.
     * @param value     The value of the attribute.
     * @return A Target object for the specified attribute and value.
     */
    Target attribute(Html.Attribute attribute, String value);
}
