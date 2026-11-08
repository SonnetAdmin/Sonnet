package sonnet.reference.selenium;

import sonnet.reference.selenium.locators.Target;

interface NavigationInterface {

    /**
     * Navigates to the leaf or last element in a structure.
     * <p>
     * <b>Example usage:</b>
     * <pre>
     *     page.leaf();
     * </pre>
     */
    void leaf();

    /**
     * Scrolls the page to the specified target element.
     * <p>
     * <b>Example usage:</b>
     * <pre>
     *     page.scroll(Target.LOGIN_BUTTON);
     * </pre>
     * @param target the target element to scroll to
     */
    void scroll(Target target);

    /**
     * Scrolls the page to a default position or currently focused element.
     * <p>
     * <b>Example usage:</b>
     * <pre>
     *     page.scroll();
     * </pre>
     */
    void scroll();

    // DOM navigation

    /**
     * Descends into the DOM tree from the current element.
     * <p>
     * <b>Example usage:</b>
     * <pre>
     *     page.descend();
     * </pre>
     */
    void descend();

    /**
     * Descends into the DOM tree to the specified target element.
     * <p>
     * <b>Example usage:</b>
     * <pre>
     *     page.descend(Target.SECTION);
     * </pre>
     * @param target the element to descend to
     */
    void descend(Target target);

    /**
     * Ascends in the DOM tree from the current element.
     * <p>
     * <b>Example usage:</b>
     * <pre>
     *     page.ascend();
     * </pre>
     */
    void ascend();

    /**
     * Ascends a specified number of levels in the DOM tree.
     * <p>
     * <b>Example usage:</b>
     * <pre>
     *     page.ascend(2);
     * </pre>
     * @param levels the number of levels to ascend
     */
    void ascend(int levels);

    /**
     * Ascends to the element at the specified index in the DOM tree.
     * <p>
     * <b>Example usage:</b>
     * <pre>
     *     page.ascend(Enums.Index.FIRST);
     * </pre>
     * @param index the index to ascend to
     */
    void ascend(Enums.Index index);

    /**
     * Ascends to the element with the specified tag in the DOM tree.
     * <p>
     * <b>Example usage:</b>
     * <pre>
     *     page.ascend(Enums.Tag.DIV);
     * </pre>
     * @param tag the tag to ascend to
     */
    void ascend(Enums.Tag tag);

    /**
     * Traverses the DOM tree from the current element.
     * <p>
     * <b>Example usage:</b>
     * <pre>
     *     page.traverse();
     * </pre>
     */
    void traverse();

    /**
     * Traverses the DOM tree to the element at the specified index.
     * <p>
     * <b>Example usage:</b>
     * <pre>
     *     page.traverse(2);
     * </pre>
     * @param index the index to traverse to
     */
    void traverse(int index);

    /**
     * Traverses the DOM tree to the element at the specified node enum.
     * <p>
     * <b>Example usage:</b>
     * <pre>
     *     page.traverse(Enums.NodeEnum.NEXT);
     * </pre>
     * @param index the node enum to traverse to
     */
    void traverse(Enums.NodeEnum index);


    /**
     * Reverses the traversal in the DOM tree from the current element.
     * <p>
     * <b>Example usage:</b>
     * <pre>
     *     page.reverse();
     * </pre>
     */
    void reverse();

    /**
     * Reverses the traversal to the element at the specified index.
     * <p>
     * <b>Example usage:</b>
     * <pre>
     *     page.reverse(2);
     * </pre>
     * @param index the index to reverse to
     */
    void reverse(int index);

    /**
     * Reverses the traversal to the element at the specified node enum.
     * <p>
     * <b>Example usage:</b>
     * <pre>
     *     page.reverse(Enums.NodeEnum.PREVIOUS);
     * </pre>
     * @param index the node enum to reverse to
     */
    void reverse(Enums.NodeEnum index);
}
