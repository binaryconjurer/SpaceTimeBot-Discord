package pro.lurk.command.CustomEmbed;

public class CustomEmbedField /*implements Comparable<CustomEmbedField> */{

	// Properties of a Discord Embed Field
	// orderNumber should be anywhere from 1-25
//	private int fieldOder;
	private String fieldTitle;
	private String fieldDescription;
	private boolean inline = false;

	public CustomEmbedField(String fieldTitle, String fieldDescrption, boolean inline) {
//		this.fieldOder = fieldOrder;
		this.fieldTitle = fieldTitle;
		this.fieldDescription = fieldDescrption;
		this.inline = inline;
	}
	public CustomEmbedField() {
		
	}

//	public int getFieldOrder() {
//		return fieldOder;
//	}
//
//	public void setOrderNumber(int order) {
//		this.fieldOder = order;
//	}

	public String getFieldTitle() {
		return fieldTitle;
	}

	public void setFieldTitle(String fieldTitle) {
		this.fieldTitle = fieldTitle;
	}

	public String getFieldDescription() {
		return fieldDescription;
	}

	public void setFieldDescription(String fieldDescription) {
		this.fieldDescription = fieldDescription;
	}

	public boolean isInline() {
		return inline;
	}

	public void setInline(boolean inline) {
		this.inline = inline;
	}

	@Override
	public String toString() {
		return "CustomEmbedField [order=" + " fieldName=" + fieldTitle + ", fieldDescription="
				+ fieldDescription + ", inline=" + inline + "]";
	}

	// Orders fields that are in a collection to be sorted by the field order number
//	@Override
//	public int compareTo(CustomEmbedField field) {
//		return this.getFieldOrder() - field.getFieldOrder();
//	}

}
