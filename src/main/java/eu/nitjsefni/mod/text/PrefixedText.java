package eu.nitjsefni.mod.text;

import java.util.List;

import net.minecraft.text.OrderedText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextContent;

/**
 *
 * @author jotty
 *
 */
public class PrefixedText implements Text {

	private final String pattern;
	private final Text text;

	private PrefixedText(String pattern, Text text) {
		this.pattern = pattern;
		this.text = text;
	}

	public final static PrefixedText instance(String pattern, Text text) {
		return new PrefixedText(pattern, text);
	}

	private Text generateText() {
		return Text.of(pattern.replace("%s", text.getString()));
	}

	@Override
	public OrderedText asOrderedText() {
		return generateText().asOrderedText();
	}

	@Override
	public TextContent getContent() {
		return generateText().getContent();
	}

	@Override
	public List<Text> getSiblings() {
		return generateText().getSiblings();
	}

	@Override
	public Style getStyle() {
		return text.getStyle();
	}
}
