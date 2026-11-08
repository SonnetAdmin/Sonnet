package sonnet.reference.selenium;

import lombok.Getter;

public class Enums {

    public enum Index {
        FIRST,
        SECOND,
        THIRD,
        FOURTH,
        FIFTH,
        SIXTH,
        SEVENTH,
        EIGHTH,
        NINTH,
        TENTH;
    }

    public enum NodeEnum {
        FIRST(1),
        SECOND(2),
        THIRD(3),
        FOURTH(4),
        FIFTH(5),
        SIXTH(6),
        SEVENTH(7),
        EIGHTH(8),
        NINTH(9),
        TENTH(10);

        private final int value;

        NodeEnum(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public enum ListIndex {
        FIRST,
        LAST,
        NEXT,
        PREVIOUS;
    }

    @Getter
    public enum Level {
        ZERO(0),
        ONE(1),
        TWO(2),
        THREE(3),
        FOUR(4),
        FIVE(5),
        SIX(6),
        SEVEN(7),
        EIGHT(8),
        NINE(9),
        TEN(10);

        private final int value;

        Level(int value) {
            this.value = value;
        }

    }

    public enum NodeType {
        CHILDREN,
        SIBLINGS

    }

    public enum Relation {
        CHILD,
        GRANDPARENT,
        PARENT,
        SIBLING
    }

    public enum Tag {
        A,
        ABBR,
        ACRONYM,
        ADDRESS,
        AREA,
        ARTICLE,
        ASIDE,
        AUDIO,
        B,
        BASE,
        BDI,
        BDO,
        BIG,
        BLOCKQUOTE,
        BODY,
        BR,
        BUTTON,
        CANVAS,
        CAPTION,
        CENTER,
        CITE,
        CODE,
        COL,
        COLGROUP,
        DATA,
        DATALIST,
        DD,
        DEL,
        DETAILS,
        DFN,
        DIALOG,
        DIR,
        DIV,
        DL,
        DT,
        EM,
        EMBED,
        FENCEDFRAME,
        FIELDSET,
        FIGCAPTION,
        FIGURE,
        FONT,
        FOOTER,
        FORM,
        FRAME,
        FRAMESET,
        H1,
        H2,
        H3,
        H4,
        H5,
        H6,
        HEAD,
        HEADER,
        HGROUP,
        HR,
        HTML,
        I,
        IFRAME,
        IMG,
        INPUT,
        INS,
        KBD,
        LABEL,
        LEGEND,
        LI,
        LINK,
        MAIN,
        MAP,
        MARK,
        MARQUEE,
        MENU,
        META,
        METER,
        NAV,
        NOBR,
        NOEMBED,
        NOFRAMES,
        NOSCRIPT,
        OBJECT,
        OL,
        OPTGROUP,
        OPTION,
        OUTPUT,
        P,
        PARAM,
        PICTURE,
        PLAINTEXT,
        PORTAL,
        PRE,
        PROGRESS,
        Q,
        RB,
        RP,
        RT,
        RTC,
        RUBY,
        S,
        SAMP,
        SCRIPT,
        SEARCH,
        SECTION,
        SELECT,
        SLOT,
        SMALL,
        SOURCE,
        SPAN,
        STRIKE,
        STRONG,
        STYLE,
        SUB,
        SUMMARY,
        SUP,
        TABLE,
        TBODY,
        TD,
        TEMPLATE,
        TEXTAREA,
        TFOOT,
        TH,
        THEAD,
        TIME,
        TITLE,
        TR,
        TRACK,
        TT,
        U,
        UL,
        VAR,
        VIDEO,
        WBR,
        XMP,
        SVG,
        G;
    }

    public enum ExpectedCondition {
        CLICKABLE,
        PRESENCE,
        VISIBILITY
    }

    public enum Direction {
        BACK,
        FORWARD,
        REFRESH
    }

    public enum ElementTrait {
        ALT,
        ATTRIBUTES,
        CLASS,
        CONTENT,
        DISPLAYED,
        ENABLED,
        HREF,
        ID,
        LANG,
        NAME,
        PLACEHOLDER,
        SELECTED,
        STYLE,
        TAG,
        TEXT,
        TITLE,
        VALUE
    }

    public enum ElementStatus {
        ASYNC,
        AUTOFOCUS,
        AUTOPLAY,
        CHECKED,
        COMPACT,
        COMPLETE,
        CONTROLS,
        DECLARE,
        DEFAULTCHECKED,
        DEFAULTSELECTED,
        DEFER,
        DISABLED,
        DRAGGABLE,
        ENDED,
        FORMNOVALIDATE,
        HIDDEN,
        INDETERMINATE,
        ISCONTENTEDITABLE,
        ISMAP,
        ITEMSCOPE,
        LOOP,
        MUTED,
        NOHREF,
        NORESIZE,
        NOSHADE,
        NOVALIDATE,
        NOWRAP,
        OPEN,
        PAUSED,
        PUBDATE,
        READONLY,
        REQUIRED,
        REVERSED,
        SCOPED,
        SEAMLESS,
        SEEKING,
        SELECTED,
        SPELLCHECK,
        TRUESPEED,
        WILLVALIDATE;
    }

    public enum Browser {
        CHROME,
        FIREFOX,
        EDGE,
        SAFARI;
    }

    public enum Targets {
        SINGLE,
        MULTIPLE;
    }

    public enum ElementState {
        ABSENCE,
        INVISIBILITY,
        PRESENCE,
        VISIBLITY;
    }

    @Getter
    public enum Attribute {
        ALT("alt"),
        CLASS("class"),
        CONTENTEDITABLE("contenteditable"),
        DATA_TESTID("data-testid"),
        HREF("href"),
        ID("id"),
        IMG("img"),
        NAME("name"),
        PLACEHOLDER("placeholder"),
        ROLE("role"),
        SRC("src"),
        STYLE("style"),
        TABINDEX("tabindex"),
        TITLE("title"),
        TYPE("type"),
        VALUE("value");

        private final String value;

        Attribute(String value) {
            this.value = value;
        }
    }

    public enum Content {
        PRESENT,
        ABSENT;
    }

    public enum Event {
        BLUR,
        CHANGE,
        CLICK,
        DBLCLICK,
        FOCUS;
    }

    @Getter
    public enum Window {
        HOME(0),
        FIRST(0),
        SECOND(1),
        THIRD(2);

        private final int value;

        Window(int value) {
            this.value = value;
        }
    }

    @Getter
    //HTML type e.g. <input type="email"
    public enum Type {
        BUTTON("button"),
        CHECKBOX("checkbox"),
        DATE("date"),
        EMAIL("email"),
        FILE("file"),
        HIDDEN("hidden"),
        IMAGE("image"),
        MONTH("month"),
        NUMBER("number"),
        PASSWORD("password"),
        RADIO("radio"),
        RESET("reset"),
        SEARCH("search"),
        SUBMIT("submit"),
        TEXT("text"),
        TIME("time"),
        URL("url"),
        WEEK("week");

        private final String value;

        Type(String value) {
            this.value = value;
        }

    }
}
