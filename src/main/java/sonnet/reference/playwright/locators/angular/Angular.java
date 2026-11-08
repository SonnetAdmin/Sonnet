package sonnet.reference.playwright.locators.angular;


//////////////////////////////////
// Angular specific definitions //
//////////////////////////////////

import sonnet.reference.playwright.CommonPageObject;
import sonnet.reference.playwright.locators.Target;

import static sonnet.reference.playwright.locators.Target.Type.*;

public class Angular extends CommonPageObject
{
    /////////////
    // TARGETS //
    /////////////


    /**
     *  <p style="color:green"><b>Angular Utility</b></p>
     *  Create a Target with attribute data_ci_id
     * <p style="color:blue"><b>Example :</b></p>
     * <p><b><i><code>focus(data_ci_id("SendButton").click();
     * @param value The data_ci_id attribute
     * @return Target
     */


    public static Target data_ci_id(String value) {return new Target("[data-ci-id='" + value + "']", "data-ci-id", value, ATTRIBUTE_VALUE );  }

    protected static Target partial_data_ci_id(String value) {
        return  new Target("//*[contains(@data-ci-id, '" + value + "')]", "data-ci-id", value, PARTIAL_ATTRIBUTE );  }

    protected static Target partial_class(String value) {
        return  new Target("//*[contains(@class, '" + value + "')]", "class", value, PARTIAL_ATTRIBUTE );  }
    
    public static Target data_id(String value) {
        return new Target("[data-id='" + value + "']", "data-id", value, ATTRIBUTE_VALUE );  }

    public static Target data_ci_name(String name) {
        return new Target("[data-ci-name='" + name + "']", "data-ci-name", name, ATTRIBUTE_VALUE );  }

    public enum Tag
    {
        CDK_DIALOG_CONTAINER("cdk-dialog-container"),
        MAT_CARD("mat-card"),
        MAT_CARD_CONTENT("mat-card-content"),
        MAT_CARD_FOOTER("mat-card-footer"),
        MAT_CARD_HEADER("mat-card-header"),
        MAT_CARD_SUBTITLE("mat-card-subtitle"),
        MAT_CARD_TITLE("mat-card-title"),
        MAT_CHECKBOX("mat-checkbox"),
        MAT_DIALOG_CONTAINER("mat-dialog-container"),
        MAT_ERROR("mat-error"),
        MAT_EXPANSION_PANEL("mat-expansion-panel"),
        MAT_EXPANSION_PANEL_HEADER("mat-expansion-panel-header"),
        MAT_FORM_FIELD("mat-form-field"),
        MAT_HINT("mat-hint"),
        MAT_ICON("mat-icon"),
        MAT_LABEL("mat-label"),
        MAT_LIST("mat-list"),
        MAT_LIST_ITEM("mat-list-item"),
        MAT_OPTION("mat-option"),
        MAT_PAGINATOR("mat-paginator"),
        MAT_PANEL_DESCRIPTION("mat-panel-description"),
        MAT_PANEL_TITLE("mat-panel-title"),
        MAT_PROGRESS_SPINNER("mat-progress-spinner"),
        MAT_RADIO_BUTTON("mat-radio-button"),
        MAT_SELECT("mat-select"),
        MAT_SELECTION_LIST("mat-selection-list"),
        MAT_SNACK_BAR_CONTAINER("mat-snack-bar-container"),
        TA("ta"),  // Yes, really - a new Angular tag.
        TN_ALERT("tn-alert"),
        TN_BADGE("tn-badge"),
        TN_BUTTON("tn-button"),
        TN_CARD("tn-card"),
        TN_CHECKBOX_EXPANSION_PANEL("tn-checkbox-expansion-panel"),
        TN_CHECKBOX_EXPANSION_PANEL_HEADER("mat-expansion-panel-header"),
        TN_DIALOG_FORM("tn-dialog-form"),
        TN_LABEL("tn-label"),
        TN_DIALOG_PANEL("tn-dialog-panel"),
        TN_DYNAMIC_TABLE("tn-dynamic-table"),
        TN_EXPANSION_PANEL("tn-expansion-panel"),
        TN_EXPANSION_PANEL_HEADER("tn-expansion-panel-header"),
        TN_ICON("tn-icon"),
        TN_LABEL_VALUE_REVEAL("tn-label-value-reveal"),
        TN_LIST_PAGE_GRID("tn-list-page-grid"),
        TN_LIST_PAGE_TITLE("tn-list-page-title"),
        TN_LIST_TABLE("tn-list-table"),
        TN_NAVIGATION("tn-navigation"),
        TN_PAGE_TITLE("tn-page-title"),
        TN_RADIO_GROUP("tn-radio-group"),
        TN_TABLE_ROW("tn-table-row"),
        TN_UPLOAD("tn-upload");

        
        private String value;
        Tag(String value){
            this.value = value;
        }
        public String getValue() { return value;
        }
    }
    public static Target tagWithText(Tag tag, String text) {
        String t = text.trim();
        return new Target("//" + tag.getValue() + "[contains(normalize-space(text()), '" + t + "')]", tag.getValue(), t, TAG_WITH_TEXT );
    }

    public static Target tag(Tag tag) {
        return new Target(tag.getValue(), tag.getValue(), TAG );
    }

    // TODO used only once in V2.  Replace in V2 and delete from here.
    protected static Target dataIconName(String value) {
        return new Target("[data-mat-icon-name='" + value + "']", "data-mat-icon-name", value, ATTRIBUTE_VALUE); }

    @Deprecated
    protected static Target tagWithId(Tag tag, String id) {
       return new Target("//" + tag.getValue() + "[@id='" + id + "']", tag.getValue(), id, TAG_WITH_ID );
    }
}
