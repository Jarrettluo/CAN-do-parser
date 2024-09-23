package com.jiaruiblog;

public class DbcParser {

  final List<0bject> SIGNAL_LIST = new ArrayList<>;

  static final List<Object> SIGNAL_NAME = new ArrayList<>();

  static final List<0bject> MESSAGE_NAME = new ArrayList<>(;

  public static final Pattern NODE_PATTERN = Pattern.compile(, Pattern.DOTALL);
3 usages
  public static final Pattern MESSAGE_PATTERN = Pattern.compile( regex: "ABO_ (.*?) (-*3): (.*3) C.*)", Pattern. DOTALL);
2 usages
  public static final Pattern SIGNAL_PATTERN =
  Pattern. compile( regex:
  "AIN\t| ISG_
  （.*）3：（・*）
  （・*）@（-*） IICO*）CK3）W MEGR）WICR）MN"C★2）（*）"，Pattern.DOTALL）；
  // dbc的属性定义部分

  public static final Pattern BA_PATTERN = Pattern.compile regex:
  "ABA-
  \"SystemSignalLongSymboL\" SG.
  private static BufferedReader readDbc(String filePath) throws IOException f
  String charset = getEncoding (filePath) .orElse( other: "windows-1252");
  InputStream inputStream = new
  FileInputStream(fiLePath):
  InputStreamReader reader = new InputstreamReader (inputStream, Charset. forName (charset)) ;
    return new BufferedReader (reader);
  }
}
