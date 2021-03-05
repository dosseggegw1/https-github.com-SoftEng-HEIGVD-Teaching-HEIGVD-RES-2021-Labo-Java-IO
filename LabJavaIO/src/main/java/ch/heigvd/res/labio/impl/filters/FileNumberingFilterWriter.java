package ch.heigvd.res.labio.impl.filters;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.logging.Logger;
/**
 * This class transforms the streams of character sent to the decorated writer.
 * When filter encounters a line separator, it sends it to the decorated writer.
 * It then sends the line number and a tab character, before resuming the write
 * process.
 *
 * Hello\n\World -> 1\tHello\n2\tWorld
 *
 * @author Olivier Liechti
 */
public class FileNumberingFilterWriter extends FilterWriter {
  private static final Logger LOG = Logger.getLogger(FileNumberingFilterWriter.class.getName());
  private int lineCounter = 0;
  private char lastChar;

  public FileNumberingFilterWriter(Writer out) {
    super(out);
  }

  @Override
  public void write(String str, int off, int len) throws IOException {
    super.write(str.toCharArray(), off, len);
    //throw new UnsupportedOperationException("The student has not implemented this method yet.");
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    for(char c : cbuf){
      super.write(c);
    }
  }

  @Override
  public void write(int c) throws IOException {
    char letter = (char) c;
    if(lineCounter == 0){
      super.write((++lineCounter) + '\t' + letter);
    } else if (lastChar == '\r' && letter == '\n'){
      super.write(letter);
    }else if (letter == '\r' || letter == '\n'){
      super.write(letter + (++lineCounter) + '\t');
    }else {
      super.write (letter);
    }
    lastChar = letter;
  }
}
