package sonnet.reference.playwright;

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
        TENTH,
        ELEVENTH,
        TWELFTH,
        THIRTEENTH,
        FOURTEENTH,
        FIFTEENTH,
        SIXTEENTH,
        SEVENTEENTH,
        EIGHTEENTH,
        NINETEENTH,
        TWENTIETH;
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

    public enum Scroll {
        UP,
        DOWN,
        HOME,
        END
    }

    public enum LogStatus {
        ON,
        OFF
    }

    public enum ElementTrait {
        ALT,
        ATTRIBUTES,
        CLASS,
        CONTENT,
        DIMX,
        DIMY,
        DISPLAYED,
        ENABLED,
        HREF,
        ID,
        LANG,
        NAME,
        PLACEHOLDER,
        POSX,
        POSY,
        REQUIRED,
        SELECTED,
        STYLE,
        TAG,
        TEXT,
        TEXT_TRIMMED,
        TITLE,
        TYPE,
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
        ACCEPT("accept"),
        ACCEPT_CHARSET("accept-charset"),
        ACCESSKEY("accesskey"),
        ACTION("action"),
        ALIGN("align"),
        ALT("alt"),
        ASYNC("async"),
        AUTOCOMPLETE("autocomplete"),
        AUTOFOCUS("autofocus"),
        AUTOPLAY("autoplay"),
        BGCOLOR("bgcolor"),
        BORDER("border"),
        CHARSET("charset"),
        CHECKED("checked"),
        CITE("cite"),
        CLASS("class"),
        COLOR("color"),
        COLS("cols"),
        COLSPAN("colspan"),
        CONTENT("content"),
        CONTENTEDITABLE("contenteditable"),
        CONTROLS("controls"),
        COORDS("coords"),
        DATA("data-"),
        DATETIME("datetime"),
        DEFAULT("default"),
        DEFER("defer"),
        DIR("dir"),
        DIRNAME("dirname"),
        DISABLED("disabled"),
        DOWNLOAD("download"),
        DRAGGABLE("draggable"),
        ENCTYPE("enctype"),
        FOR("for"),
        FORM("form"),
        FORMACTION("formaction"),
        HEADERS("headers"),
        HEIGHT("height"),
        HIDDEN("hidden"),
        HIGH("high"),
        HREF("href"),
        HREFLANG("hreflang"),
        HTTP_EQUIV("http-equiv"),
        ID("id"),
        ISMAP("ismap"),
        KIND("kind"),
        LABEL("label"),
        LANG("lang"),
        LIST("list"),
        LOOP("loop"),
        LOW("low"),
        MAX("max"),
        MAXLENGTH("maxlength"),
        MEDIA("media"),
        METHOD("method"),
        MIN("min"),
        MULTIPLE("multiple"),
        MUTED("muted"),
        NAME("name"),
        NOVALIDATE("novalidate"),
        OPEN("open"),
        OPTIMUM("optimum"),
        PATTERN("pattern"),
        PLACEHOLDER("placeholder"),
        POSTER("poster"),
        PRELOAD("preload"),
        READONLY("readonly"),
        REL("rel"),
        REQUIRED("required"),
        REVERSED("reversed"),
        ROLE("role"),
        ROWS("rows"),
        ROWSPAN("rowspan"),
        SANDBOX("sandbox"),
        SCOPE("scope"),
        SELECTED("selected"),
        SHAPE("shape"),
        SIZE("size"),
        SIZES("sizes"),
        SPAN("span"),
        SPELLCHECK("spellcheck"),
        SRC("src"),
        SRCDOC("srcdoc"),
        SRCLANG("srclang"),
        START("start"),
        STEP("step"),
        STYLE("style"),
        TABINDEX("tabindex"),
        TARGET("target"),
        TITLE("title"),
        TYPE("type"),
        USEMAP("usemap"),
        VALUE("value"),
        WIDTH("width"),
        WRAP("wrap");

        private final String value;

        Attribute(String value) {
            this.value = value;
        }
    }

    public enum Content {
        PRESENT,
        ABSENT;
    }

    @Getter
    public enum Event {
        BLUR("blur"),
        CHANGE("change"),
        CLICK("click"),
        DBLCLICK("dblclick"),
        FOCUS("focus"),
        KEY_DOWN("keydown"),
        KEY_UP("keyup");

        private final String value;

        Event(String value) {
            this.value = value;
        }
    }

    @Getter
    public enum Window {
        HOME(0),
        SECOND(1),
        THIRD(2);

        private final int value;

        Window(int value) {
            this.value = value;
        }
    }

    @Getter
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

    @Getter
    public enum ScreenSize {
        FULL(1920, 1080),
        HALF(960, 1080),
        TABLET(768, 1024),
        PHONE(375, 812);

        private final int width;
        private final int height;

        ScreenSize(int width, int height) {
            this.width = width;
            this.height = height;
        }
    }

    public enum DateFormat {
        UK("dd/MM/yyyy"),
        US("MM/dd/yyyy");

        @Getter
        private final String pattern;

        DateFormat(String pattern) {
            this.pattern = pattern;
        }
    }

    public enum Selector {
        VALUE,
        LABEL,
    }
}
