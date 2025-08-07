package nanoxml;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.CharArrayReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.microedition.media.control.ToneControl;
import org.objectweb.asm.Opcodes;

/* loaded from: avacs.jar:nanoxml/XMLElement.class */
public class XMLElement {
    static final long serialVersionUID = 6685035139346394777L;
    public static final int NANOXML_MAJOR_VERSION = 2;
    public static final int NANOXML_MINOR_VERSION = 2;
    private Hashtable attributes;
    private Vector children;
    private String name;
    private String contents;
    private Hashtable entities;
    private int lineNr;
    private boolean ignoreCase;
    private boolean ignoreWhitespace;
    private char charReadTooMuch;
    private Reader reader;
    private int parserLineNr;

    public XMLElement() {
        this(new Hashtable(), false, true, true);
    }

    public XMLElement(boolean skipLeadingWhitespace, boolean ignoreCase) {
        this(new Hashtable(), skipLeadingWhitespace, true, ignoreCase);
    }

    public XMLElement(Hashtable entities) {
        this(entities, false, true, true);
    }

    public XMLElement(boolean skipLeadingWhitespace) {
        this(new Hashtable(), skipLeadingWhitespace, true, true);
    }

    public XMLElement(Hashtable entities, boolean skipLeadingWhitespace) {
        this(entities, skipLeadingWhitespace, true, true);
    }

    public XMLElement(Hashtable entities, boolean skipLeadingWhitespace, boolean ignoreCase) {
        this(entities, skipLeadingWhitespace, true, ignoreCase);
    }

    protected XMLElement(Hashtable entities, boolean skipLeadingWhitespace, boolean fillBasicConversionTable, boolean ignoreCase) {
        this.ignoreWhitespace = skipLeadingWhitespace;
        this.ignoreCase = ignoreCase;
        this.name = null;
        this.contents = "";
        this.attributes = new Hashtable();
        this.children = new Vector();
        this.entities = entities;
        this.lineNr = 0;
        Enumeration en = this.entities.keys();
        while (en.hasMoreElements()) {
            Object key = en.nextElement();
            Object value = this.entities.get(key);
            if (value instanceof String) {
                this.entities.put(key, ((String) value).toCharArray());
            }
        }
        if (fillBasicConversionTable) {
            this.entities.put("amp", new char[]{'&'});
            this.entities.put("quot", new char[]{'\"'});
            this.entities.put("apos", new char[]{'\''});
            this.entities.put("lt", new char[]{'<'});
            this.entities.put("gt", new char[]{'>'});
        }
    }

    public void addChild(XMLElement child) {
        this.children.addElement(child);
    }

    public XMLElement addChild(String name) {
        XMLElement xml = new XMLElement(true, false);
        xml.setName(name);
        addChild(xml);
        return xml;
    }

    public XMLElement addChild(String name, String value) {
        XMLElement xml = addChild(name);
        xml.setContent(value);
        return xml;
    }

    public void setAttribute(String name, Object value) {
        if (this.ignoreCase) {
            name = name.toUpperCase();
        }
        this.attributes.put(name, value.toString());
    }

    public void addProperty(String name, Object value) {
        setAttribute(name, value);
    }

    public void setIntAttribute(String name, int value) {
        if (this.ignoreCase) {
            name = name.toUpperCase();
        }
        this.attributes.put(name, Integer.toString(value));
    }

    public void addProperty(String key, int value) {
        setIntAttribute(key, value);
    }

    public void setDoubleAttribute(String name, double value) {
        if (this.ignoreCase) {
            name = name.toUpperCase();
        }
        this.attributes.put(name, Double.toString(value));
    }

    public void addProperty(String name, double value) {
        setDoubleAttribute(name, value);
    }

    public int countChildren() {
        return this.children.size();
    }

    public Enumeration enumerateAttributeNames() {
        return this.attributes.keys();
    }

    public Enumeration enumeratePropertyNames() {
        return enumerateAttributeNames();
    }

    public Enumeration enumerateChildren() {
        return this.children.elements();
    }

    public Vector getChildren() {
        try {
            return (Vector) this.children.clone();
        } catch (Exception e) {
            return null;
        }
    }

    public XMLElement getChild(String name) {
        Enumeration en = this.children.elements();
        while (en.hasMoreElements()) {
            XMLElement el = (XMLElement) en.nextElement();
            if (el.getName().equals(name)) {
                return el;
            }
        }
        return null;
    }

    public XMLElement getChildOrNew(String name) {
        XMLElement c = getChild(name);
        if (c == null) {
            c = addChild(name);
        }
        return c;
    }

    public int getChildCount(String name) {
        int cnt = 0;
        Enumeration en = this.children.elements();
        while (en.hasMoreElements()) {
            XMLElement el = (XMLElement) en.nextElement();
            if (el.getName().equals(name)) {
                cnt++;
            }
        }
        return cnt;
    }

    public XMLElement getChild(String name, String attrValue) {
        Enumeration en = this.children.elements();
        while (en.hasMoreElements()) {
            XMLElement el = (XMLElement) en.nextElement();
            String elAttrValue = el.getStringAttribute("name");
            if (el.getName().equals(name) && (attrValue == elAttrValue || attrValue.equals(elAttrValue))) {
                return el;
            }
        }
        return null;
    }

    public XMLElement getChild(String name, Map equalAttributesNameValue) {
        Enumeration en = this.children.elements();
        while (en.hasMoreElements()) {
            XMLElement el = (XMLElement) en.nextElement();
            if (el.getName().equals(name)) {
                for (Map.Entry atrNameValue : equalAttributesNameValue.entrySet()) {
                    String attrValue = el.getStringAttribute((String) atrNameValue.getKey());
                    if (atrNameValue.getValue() == null) {
                        if (attrValue != null) {
                            break;
                        }
                    } else if (!atrNameValue.getValue().equals(attrValue)) {
                        break;
                    }
                }
                return el;
            }
        }
        return null;
    }

    public int getChildInteger(String name, int defaultValue) {
        XMLElement xml = getChild(name);
        if (xml == null) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(xml.getContent());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public String getChildString(String name, String defaultValue) {
        XMLElement xml = getChild(name);
        if (xml == null) {
            return defaultValue;
        }
        String v = xml.getContent();
        if (v == null || v.length() == 0) {
            return defaultValue;
        }
        return v;
    }

    public String getContents() {
        return getContent();
    }

    public String getContent() {
        return this.contents;
    }

    public int getLineNr() {
        return this.lineNr;
    }

    public Object getAttribute(String name) {
        return getAttribute(name, null);
    }

    public Object getAttribute(String name, Object defaultValue) {
        if (this.ignoreCase) {
            name = name.toUpperCase();
        }
        Object value = this.attributes.get(name);
        if (value == null) {
            value = defaultValue;
        }
        return value;
    }

    public Object getAttribute(String name, Hashtable valueSet, String defaultKey, boolean allowLiterals) {
        if (this.ignoreCase) {
            name = name.toUpperCase();
        }
        Object key = this.attributes.get(name);
        if (key == null) {
            key = defaultKey;
        }
        Object result = valueSet.get(key);
        if (result == null) {
            if (allowLiterals) {
                result = key;
            } else {
                throw invalidValue(name, (String) key);
            }
        }
        return result;
    }

    public String getStringAttribute(String name) {
        return getStringAttribute(name, null);
    }

    public Map getStringAttributes(String[] names) {
        Map attrNameValue = new HashMap();
        for (int i = 0; i < names.length; i++) {
            attrNameValue.put(names[i], getStringAttribute(names[i]));
        }
        return attrNameValue;
    }

    public String getStringAttribute(String name, String defaultValue) {
        return (String) getAttribute(name, defaultValue);
    }

    public String getStringAttribute(String name, Hashtable valueSet, String defaultKey, boolean allowLiterals) {
        return (String) getAttribute(name, valueSet, defaultKey, allowLiterals);
    }

    public int getIntAttribute(String name) {
        return getIntAttribute(name, 0);
    }

    public int getIntAttribute(String name, int defaultValue) {
        if (this.ignoreCase) {
            name = name.toUpperCase();
        }
        String value = (String) this.attributes.get(name);
        if (value == null) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw invalidValue(name, value);
        }
    }

    public int getIntAttribute(String name, Hashtable valueSet, String defaultKey, boolean allowLiteralNumbers) throws NumberFormatException {
        if (this.ignoreCase) {
            name = name.toUpperCase();
        }
        Object key = this.attributes.get(name);
        if (key == null) {
            key = defaultKey;
        }
        try {
            Integer result = (Integer) valueSet.get(key);
            if (result == null) {
                if (!allowLiteralNumbers) {
                    throw invalidValue(name, (String) key);
                }
                try {
                    result = Integer.valueOf((String) key);
                } catch (NumberFormatException e) {
                    throw invalidValue(name, (String) key);
                }
            }
            return result.intValue();
        } catch (ClassCastException e2) {
            throw invalidValueSet(name);
        }
    }

    public double getDoubleAttribute(String name) {
        return getDoubleAttribute(name, 0.0d);
    }

    public double getDoubleAttribute(String name, double defaultValue) {
        if (this.ignoreCase) {
            name = name.toUpperCase();
        }
        String value = (String) this.attributes.get(name);
        if (value == null) {
            return defaultValue;
        }
        try {
            return Double.valueOf(value).doubleValue();
        } catch (NumberFormatException e) {
            throw invalidValue(name, value);
        }
    }

    public double getDoubleAttribute(String name, Hashtable valueSet, String defaultKey, boolean allowLiteralNumbers) throws NumberFormatException {
        if (this.ignoreCase) {
            name = name.toUpperCase();
        }
        Object key = this.attributes.get(name);
        if (key == null) {
            key = defaultKey;
        }
        try {
            Double result = (Double) valueSet.get(key);
            if (result == null) {
                if (!allowLiteralNumbers) {
                    throw invalidValue(name, (String) key);
                }
                try {
                    result = Double.valueOf((String) key);
                } catch (NumberFormatException e) {
                    throw invalidValue(name, (String) key);
                }
            }
            return result.doubleValue();
        } catch (ClassCastException e2) {
            throw invalidValueSet(name);
        }
    }

    public boolean getBooleanAttribute(String name, String trueValue, String falseValue, boolean defaultValue) {
        if (this.ignoreCase) {
            name = name.toUpperCase();
        }
        Object value = this.attributes.get(name);
        if (value == null) {
            return defaultValue;
        }
        if (value.equals(trueValue)) {
            return true;
        }
        if (value.equals(falseValue)) {
            return false;
        }
        throw invalidValue(name, (String) value);
    }

    public boolean getBooleanAttribute(String name, boolean defaultValue) {
        return getBooleanAttribute(name, "true", "false", defaultValue);
    }

    public int getIntProperty(String name, Hashtable valueSet, String defaultKey) {
        return getIntAttribute(name, valueSet, defaultKey, false);
    }

    public String getProperty(String name) {
        return getStringAttribute(name);
    }

    public String getProperty(String name, String defaultValue) {
        return getStringAttribute(name, defaultValue);
    }

    public int getProperty(String name, int defaultValue) {
        return getIntAttribute(name, defaultValue);
    }

    public double getProperty(String name, double defaultValue) {
        return getDoubleAttribute(name, defaultValue);
    }

    public boolean getProperty(String key, String trueValue, String falseValue, boolean defaultValue) {
        return getBooleanAttribute(key, trueValue, falseValue, defaultValue);
    }

    public Object getProperty(String name, Hashtable valueSet, String defaultKey) {
        return getAttribute(name, valueSet, defaultKey, false);
    }

    public String getStringProperty(String name, Hashtable valueSet, String defaultKey) {
        return getStringAttribute(name, valueSet, defaultKey, false);
    }

    public int getSpecialIntProperty(String name, Hashtable valueSet, String defaultKey) {
        return getIntAttribute(name, valueSet, defaultKey, true);
    }

    public double getSpecialDoubleProperty(String name, Hashtable valueSet, String defaultKey) {
        return getDoubleAttribute(name, valueSet, defaultKey, true);
    }

    public String getName() {
        return this.name;
    }

    public String getTagName() {
        return getName();
    }

    public void parseFromReader(Reader reader) throws IOException, XMLParseException {
        parseFromReader(reader, 1);
    }

    public void parseFromReader(Reader reader, int startingLineNr) throws IOException, XMLParseException {
        this.charReadTooMuch = (char) 0;
        this.reader = reader;
        this.parserLineNr = startingLineNr;
        while (scanWhitespace() == '<') {
            char ch = readChar();
            if (ch == '!' || ch == '?') {
                skipSpecialTag(0);
            } else {
                unreadChar(ch);
                scanElement(this);
                return;
            }
        }
        throw expectedInput("<");
    }

    public void parse(InputStream is, int startingLineNr) throws IOException, XMLParseException {
        int c;
        BufferedReader dis;
        BufferedInputStream in = new BufferedInputStream(is);
        in.mark(100);
        StringBuffer fistLine = new StringBuffer();
        do {
            c = in.read();
            if (c == -1 || fistLine.length() >= 100) {
                break;
            } else {
                fistLine.append((char) c);
            }
        } while (c != 62);
        in.reset();
        String encoding = null;
        Pattern pattern = Pattern.compile("(encoding=\")([\\p{Alnum}-]+)\"");
        Matcher matcher = pattern.matcher(fistLine.toString());
        if (matcher.find()) {
            encoding = matcher.group(2);
        }
        if (encoding == null) {
            dis = new BufferedReader(new InputStreamReader(in));
        } else {
            dis = new BufferedReader(new InputStreamReader(in, encoding));
        }
        try {
            parseFromReader(dis, 1);
        } finally {
            dis.close();
            in.close();
        }
    }

    public void parseString(String string) throws XMLParseException {
        try {
            parseFromReader(new StringReader(string), 1);
        } catch (IOException e) {
        }
    }

    public void parseString(String string, int offset) throws XMLParseException {
        parseString(string.substring(offset));
    }

    public void parseString(String string, int offset, int end) throws XMLParseException {
        parseString(string.substring(offset, end));
    }

    public void parseString(String string, int offset, int end, int startingLineNr) throws XMLParseException {
        try {
            parseFromReader(new StringReader(string.substring(offset, end)), startingLineNr);
        } catch (IOException e) {
        }
    }

    public void parseCharArray(char[] input, int offset, int end) throws XMLParseException {
        parseCharArray(input, offset, end, 1);
    }

    public void parseCharArray(char[] input, int offset, int end, int startingLineNr) throws XMLParseException {
        try {
            Reader reader = new CharArrayReader(input, offset, end);
            parseFromReader(reader, startingLineNr);
        } catch (IOException e) {
        }
    }

    public void removeChild(XMLElement child) {
        this.children.removeElement(child);
    }

    public void removeChildren() {
        this.children.removeAllElements();
    }

    public void removeAttribute(String name) {
        if (this.ignoreCase) {
            name = name.toUpperCase();
        }
        this.attributes.remove(name);
    }

    public void removeProperty(String name) {
        removeAttribute(name);
    }

    public void removeChild(String name) {
        removeAttribute(name);
    }

    protected XMLElement createAnotherElement() {
        return new XMLElement(this.entities, this.ignoreWhitespace, false, this.ignoreCase);
    }

    public void setContent(String content) {
        this.contents = content;
    }

    public void setTagName(String name) {
        setName(name);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString() throws IOException {
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            OutputStreamWriter writer = new OutputStreamWriter(out);
            write(writer);
            writer.flush();
            return new String(out.toByteArray());
        } catch (IOException e) {
            return super.toString();
        }
    }

    public void write(Writer writer) throws IOException {
        writeIdented(writer, 0);
    }

    private void writeTabs(Writer writer, int level) throws IOException {
        for (int i = 0; i < level; i++) {
            writer.write(9);
        }
    }

    private void writeIdented(Writer writer, int level) throws IOException {
        if (this.name == null) {
            writeEncoded(writer, this.contents);
            return;
        }
        writeTabs(writer, level);
        writer.write(60);
        writer.write(this.name);
        if (!this.attributes.isEmpty()) {
            Enumeration en = this.attributes.keys();
            while (en.hasMoreElements()) {
                writer.write(32);
                String key = (String) en.nextElement();
                String value = (String) this.attributes.get(key);
                writer.write(key);
                writer.write(61);
                writer.write(34);
                writeEncoded(writer, value);
                writer.write(34);
            }
        }
        if (this.contents != null && this.contents.length() > 0) {
            writer.write(62);
            writeEncoded(writer, this.contents);
            writer.write(60);
            writer.write(47);
            writer.write(this.name);
            writer.write(62);
            writer.write(10);
            return;
        }
        if (this.children.isEmpty()) {
            writer.write(47);
            writer.write(62);
            writer.write(10);
            return;
        }
        writer.write(62);
        writer.write(10);
        Enumeration en2 = enumerateChildren();
        while (en2.hasMoreElements()) {
            XMLElement child = (XMLElement) en2.nextElement();
            child.writeIdented(writer, level + 1);
        }
        writeTabs(writer, level);
        writer.write(60);
        writer.write(47);
        writer.write(this.name);
        writer.write(62);
        writer.write(10);
    }

    protected void writeEncoded(Writer writer, String str) throws IOException {
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            switch (ch) {
                case '\"':
                    writer.write(38);
                    writer.write(Opcodes.LREM);
                    writer.write(Opcodes.LNEG);
                    writer.write(Opcodes.DDIV);
                    writer.write(Opcodes.INEG);
                    writer.write(59);
                    break;
                case '&':
                    writer.write(38);
                    writer.write(97);
                    writer.write(Opcodes.LDIV);
                    writer.write(Opcodes.IREM);
                    writer.write(59);
                    break;
                case '\'':
                    writer.write(38);
                    writer.write(97);
                    writer.write(Opcodes.IREM);
                    writer.write(Opcodes.DDIV);
                    writer.write(Opcodes.DREM);
                    writer.write(59);
                    break;
                case ToneControl.C4 /* 60 */:
                    writer.write(38);
                    writer.write(Opcodes.IDIV);
                    writer.write(Opcodes.INEG);
                    writer.write(59);
                    break;
                case '>':
                    writer.write(38);
                    writer.write(Opcodes.DSUB);
                    writer.write(Opcodes.INEG);
                    writer.write(59);
                    break;
                default:
                    if (ch < ' ' || ch > '~') {
                        writer.write(38);
                        writer.write(35);
                        writer.write(Opcodes.ISHL);
                        writer.write(Integer.toString(ch, 16));
                        writer.write(59);
                        break;
                    } else {
                        writer.write(ch);
                        break;
                    }
                    break;
            }
        }
    }

    protected void scanIdentifier(StringBuffer result) throws IOException {
        while (true) {
            char ch = readChar();
            if ((ch < 'A' || ch > 'Z') && ((ch < 'a' || ch > 'z') && ((ch < '0' || ch > '9') && ch != '_' && ch != '.' && ch != ':' && ch != '-' && ch <= '~'))) {
                unreadChar(ch);
                return;
            }
            result.append(ch);
        }
    }

    protected char scanWhitespace() throws IOException {
        while (true) {
            char ch = readChar();
            switch (ch) {
                case '\t':
                case '\n':
                case '\r':
                case ' ':
                default:
                    return ch;
            }
        }
    }

    protected char scanWhitespace(StringBuffer result) throws IOException {
        while (true) {
            char ch = readChar();
            switch (ch) {
                case '\t':
                case '\n':
                case ' ':
                    result.append(ch);
                    break;
                case '\r':
                    break;
                default:
                    return ch;
            }
        }
    }

    protected void scanString(StringBuffer string) throws IOException {
        char delimiter = readChar();
        if (delimiter != '\'' && delimiter != '\"') {
            throw expectedInput("' or \"");
        }
        while (true) {
            char ch = readChar();
            if (ch == delimiter) {
                return;
            }
            if (ch == '&') {
                resolveEntity(string);
            } else {
                string.append(ch);
            }
        }
    }

    protected void scanPCData(StringBuffer data) throws IOException {
        while (true) {
            char ch = readChar();
            if (ch == '<') {
                char ch2 = readChar();
                if (ch2 == '!') {
                    checkCDATA(data);
                } else {
                    unreadChar(ch2);
                    return;
                }
            } else if (ch == '&') {
                resolveEntity(data);
            } else {
                data.append(ch);
            }
        }
    }

    protected boolean checkCDATA(StringBuffer buf) throws IOException {
        char ch = readChar();
        if (ch != '[') {
            unreadChar(ch);
            skipSpecialTag(0);
            return false;
        }
        if (!checkLiteral("CDATA[")) {
            skipSpecialTag(1);
            return false;
        }
        int delimiterCharsSkipped = 0;
        while (delimiterCharsSkipped < 3) {
            char ch2 = readChar();
            switch (ch2) {
                case '>':
                    if (delimiterCharsSkipped < 2) {
                        for (int i = 0; i < delimiterCharsSkipped; i++) {
                            buf.append(']');
                        }
                        delimiterCharsSkipped = 0;
                        buf.append('>');
                        break;
                    } else {
                        delimiterCharsSkipped = 3;
                        break;
                    }
                case Opcodes.DUP2_X1 /* 93 */:
                    if (delimiterCharsSkipped < 2) {
                        delimiterCharsSkipped++;
                        break;
                    } else {
                        buf.append(']');
                        buf.append(']');
                        delimiterCharsSkipped = 0;
                        break;
                    }
                default:
                    for (int i2 = 0; i2 < delimiterCharsSkipped; i2++) {
                        buf.append(']');
                    }
                    buf.append(ch2);
                    delimiterCharsSkipped = 0;
                    break;
            }
        }
        return true;
    }

    protected void skipComment() throws IOException {
        int dashesToRead = 2;
        while (dashesToRead > 0) {
            char ch = readChar();
            if (ch == '-') {
                dashesToRead--;
            } else {
                dashesToRead = 2;
            }
        }
        if (readChar() != '>') {
            throw expectedInput(">");
        }
    }

    protected void skipSpecialTag(int bracketLevel) throws IOException {
        int tagLevel = 1;
        char stringDelimiter = 0;
        if (bracketLevel == 0) {
            char ch = readChar();
            if (ch == '[') {
                bracketLevel++;
            } else if (ch == '-') {
                char ch2 = readChar();
                if (ch2 == '[') {
                    bracketLevel++;
                } else if (ch2 == ']') {
                    bracketLevel--;
                } else if (ch2 == '-') {
                    skipComment();
                    return;
                }
            }
        }
        while (tagLevel > 0) {
            char ch3 = readChar();
            if (stringDelimiter == 0) {
                if (ch3 == '\"' || ch3 == '\'') {
                    stringDelimiter = ch3;
                } else if (bracketLevel <= 0) {
                    if (ch3 == '<') {
                        tagLevel++;
                    } else if (ch3 == '>') {
                        tagLevel--;
                    }
                }
                if (ch3 == '[') {
                    bracketLevel++;
                } else if (ch3 == ']') {
                    bracketLevel--;
                }
            } else if (ch3 == stringDelimiter) {
                stringDelimiter = 0;
            }
        }
    }

    protected boolean checkLiteral(String literal) throws IOException {
        int length = literal.length();
        for (int i = 0; i < length; i++) {
            if (readChar() != literal.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    protected char readChar() throws IOException {
        if (this.charReadTooMuch != 0) {
            char ch = this.charReadTooMuch;
            this.charReadTooMuch = (char) 0;
            return ch;
        }
        int i = this.reader.read();
        if (i < 0) {
            throw unexpectedEndOfData();
        }
        if (i == 10) {
            this.parserLineNr++;
            return '\n';
        }
        return (char) i;
    }

    protected void scanElement(XMLElement elt) throws IOException {
        char ch;
        StringBuffer buf = new StringBuffer();
        scanIdentifier(buf);
        String name = buf.toString();
        elt.setName(name);
        char cScanWhitespace = scanWhitespace();
        while (true) {
            ch = cScanWhitespace;
            if (ch == '>' || ch == '/') {
                break;
            }
            buf.setLength(0);
            unreadChar(ch);
            scanIdentifier(buf);
            String key = buf.toString();
            if (scanWhitespace() != '=') {
                throw expectedInput("=");
            }
            unreadChar(scanWhitespace());
            buf.setLength(0);
            scanString(buf);
            elt.setAttribute(key, buf);
            cScanWhitespace = scanWhitespace();
        }
        if (ch == '/') {
            if (readChar() != '>') {
                throw expectedInput(">");
            }
            return;
        }
        buf.setLength(0);
        char ch2 = scanWhitespace(buf);
        if (ch2 == '<') {
            while (true) {
                ch2 = readChar();
                if (ch2 == '!') {
                    if (checkCDATA(buf)) {
                        scanPCData(buf);
                        break;
                    }
                    ch2 = scanWhitespace(buf);
                    if (ch2 != '<') {
                        unreadChar(ch2);
                        scanPCData(buf);
                        break;
                    }
                } else {
                    buf.setLength(0);
                    break;
                }
            }
        } else {
            unreadChar(ch2);
            scanPCData(buf);
        }
        if (buf.length() == 0) {
            while (ch2 != '/') {
                if (ch2 == '!') {
                    if (readChar() != '-') {
                        throw expectedInput("Comment or Element");
                    }
                    if (readChar() != '-') {
                        throw expectedInput("Comment or Element");
                    }
                    skipComment();
                } else {
                    unreadChar(ch2);
                    XMLElement child = createAnotherElement();
                    scanElement(child);
                    elt.addChild(child);
                }
                if (scanWhitespace() != '<') {
                    throw expectedInput("<");
                }
                ch2 = readChar();
            }
            unreadChar(ch2);
        } else if (this.ignoreWhitespace) {
            elt.setContent(buf.toString().trim());
        } else {
            elt.setContent(buf.toString());
        }
        if (readChar() != '/') {
            throw expectedInput("/");
        }
        unreadChar(scanWhitespace());
        if (!checkLiteral(name)) {
            throw expectedInput(name);
        }
        if (scanWhitespace() != '>') {
            throw expectedInput(">");
        }
    }

    protected void resolveEntity(StringBuffer buf) throws IOException {
        char ch;
        StringBuffer keyBuf = new StringBuffer();
        while (true) {
            char ch2 = readChar();
            if (ch2 == ';') {
                break;
            } else {
                keyBuf.append(ch2);
            }
        }
        String key = keyBuf.toString();
        if (key.charAt(0) == '#') {
            try {
                if (key.charAt(1) == 'x') {
                    ch = (char) Integer.parseInt(key.substring(2), 16);
                } else {
                    ch = (char) Integer.parseInt(key.substring(1), 10);
                }
                buf.append(ch);
                return;
            } catch (NumberFormatException e) {
                throw unknownEntity(key);
            }
        }
        char[] value = (char[]) this.entities.get(key);
        if (value == null) {
            throw unknownEntity(key);
        }
        buf.append(value);
    }

    protected void unreadChar(char ch) {
        this.charReadTooMuch = ch;
    }

    protected XMLParseException invalidValueSet(String name) {
        String msg = "Invalid value set (entity name = \"" + name + "\")";
        return new XMLParseException(getName(), this.parserLineNr, msg);
    }

    protected XMLParseException invalidValue(String name, String value) {
        String msg = "Attribute \"" + name + "\" does not contain a valid value (\"" + value + "\")";
        return new XMLParseException(getName(), this.parserLineNr, msg);
    }

    protected XMLParseException unexpectedEndOfData() {
        return new XMLParseException(getName(), this.parserLineNr, "Unexpected end of data reached");
    }

    protected XMLParseException syntaxError(String context) {
        String msg = "Syntax error while parsing " + context;
        return new XMLParseException(getName(), this.parserLineNr, msg);
    }

    protected XMLParseException expectedInput(String charSet) {
        String msg = "Expected: " + charSet;
        return new XMLParseException(getName(), this.parserLineNr, msg);
    }

    protected XMLParseException unknownEntity(String name) {
        String msg = "Unknown or invalid entity: &" + name + ";";
        return new XMLParseException(getName(), this.parserLineNr, msg);
    }
}
