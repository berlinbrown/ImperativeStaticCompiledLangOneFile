/*
 * Copyright (c) 1999, 2006, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * 
 * This code is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License version 2 only, as published by
 * the Free Software Foundation. Oracle designates this particular file as
 * subject to the "Classpath" exception as provided by Oracle in the LICENSE
 * file that accompanied this code.
 * 
 * This code is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License version 2 for more
 * details (a copy is included in the LICENSE file that accompanied this code).
 * 
 * You should have received a copy of the GNU General Public License version 2
 * along with this work; if not, write to the Free Software Foundation, Inc., 51
 * Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 * 
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA or
 * visit www.oracle.com if you need additional information or have any
 * questions.
 * 
 * What is this application used for? : It is a simple proof of concept
 * programming language
 * 
 * OctaneMini, simple programming language in Java with one source file. The
 * entire implementation is contained in this file. A suite of test cases are
 * also provided in a separate class. 
 * 
 * URLs:
 * 
 * https://github.com/berlinbrown 
 * http://twitter.com/#!/berlinbrowndev2
 */

/*
 * ##################################################################
 * ##################################################################
 * ##################################################################
 * ##################################################################
 * NOTE HELPER CLASS, ONLY USED FOR JVM BYTECODE CONSTANTS, NOT USED IN ANY MAJOR CAPACITY
 * ##################################################################
 * ##################################################################
 * ##################################################################
 * NOTE HELPER CLASS, ONLY USED FOR JVM BYTECODE CONSTANTS, NOT USED IN ANY MAJOR CAPACITY
 * ##################################################################
 * ##################################################################
 * ##################################################################
 * 
 */
package org.berlin.staticlang;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Java compiler helper class.  Used with Java compilation. 
 * Contains Java class file constants. 
 * Source from java open jdk .
 */
public class JavaCompilerHelper {
    
  /**
   *  A JVM class file.
   */
  public static class ClassFile {
      
      public final static int JAVA_MAGIC = 0xCAFEBABE;

      /**
       * The format of the additional information varies with the tag value.
       */
      public final static int CONSTANT_Utf8 = 1;
      public final static int CONSTANT_Unicode = 2;
      public final static int CONSTANT_Integer = 3;
      public final static int CONSTANT_Float = 4;
      public final static int CONSTANT_Long = 5;
      public final static int CONSTANT_Double = 6;
      public final static int CONSTANT_Class = 7;
      public final static int CONSTANT_String = 8;
      public final static int CONSTANT_Fieldref = 9;
      public final static int CONSTANT_Methodref = 10;
      public final static int CONSTANT_InterfaceMethodref = 11;
      public final static int CONSTANT_NameandType = 12;
      
      public final static int MAX_PARAMETERS = 0xff;
      public final static int MAX_DIMENSIONS = 0xff;
      public final static int MAX_CODE = 0xffff;
      public final static int MAX_LOCALS = 0xffff;
      public final static int MAX_STACK = 0xffff;

  } // End of class //
  
  /** 
   * Bytecode instruction codes, as well as typecodes used as
   * instruction modifiers.
   */
  public interface ByteCodes {

      /**
       * Byte code instruction codes.
       */
      int illegal         = -1,
          nop             = 0,
          aconst_null     = 1,
          iconst_m1       = 2,
          iconst_0        = 3,
          iconst_1        = 4,
          iconst_2        = 5,
          iconst_3        = 6,
          iconst_4        = 7,
          iconst_5        = 8,
          lconst_0        = 9,
          lconst_1        = 10,
          fconst_0        = 11,
          fconst_1        = 12,
          fconst_2        = 13,
          dconst_0        = 14,
          dconst_1        = 15,
          bipush          = 16,
          sipush          = 17,
          ldc1            = 18,
          ldc2            = 19,
          ldc2w           = 20,
          iload           = 21,
          lload           = 22,
          fload           = 23,
          dload           = 24,
          aload           = 25,
          iload_0         = 26,
          iload_1         = 27,
          iload_2         = 28,
          iload_3         = 29,
          lload_0         = 30,
          lload_1         = 31,
          lload_2         = 32,
          lload_3         = 33,
          fload_0         = 34,
          fload_1         = 35,
          fload_2         = 36,
          fload_3         = 37,
          dload_0         = 38,
          dload_1         = 39,
          dload_2         = 40,
          dload_3         = 41,
          aload_0         = 42,
          aload_1         = 43,
          aload_2         = 44,
          aload_3         = 45,
          iaload          = 46,
          laload          = 47,
          faload          = 48,
          daload          = 49,
          aaload          = 50,
          baload          = 51,
          caload          = 52,
          saload          = 53,
          istore          = 54,
          lstore          = 55,
          fstore          = 56,
          dstore          = 57,
          astore          = 58,
          istore_0        = 59,
          istore_1        = 60,
          istore_2        = 61,
          istore_3        = 62,
          lstore_0        = 63,
          lstore_1        = 64,
          lstore_2        = 65,
          lstore_3        = 66,
          fstore_0        = 67,
          fstore_1        = 68,
          fstore_2        = 69,
          fstore_3        = 70,
          dstore_0        = 71,
          dstore_1        = 72,
          dstore_2        = 73,
          dstore_3        = 74,
          astore_0        = 75,
          astore_1        = 76,
          astore_2        = 77,
          astore_3        = 78,
          iastore         = 79,
          lastore         = 80,
          fastore         = 81,
          dastore         = 82,
          aastore         = 83,
          bastore         = 84,
          castore         = 85,
          sastore         = 86,
          pop             = 87,
          pop2            = 88,
          dup             = 89,
          dup_x1          = 90,
          dup_x2          = 91,
          dup2            = 92,
          dup2_x1         = 93,
          dup2_x2         = 94,
          swap            = 95,
          iadd            = 96,
          ladd            = 97,
          fadd            = 98,
          dadd            = 99,
          isub            = 100,
          lsub            = 101,
          fsub            = 102,
          dsub            = 103,
          imul            = 104,
          lmul            = 105,
          fmul            = 106,
          dmul            = 107,
          idiv            = 108,
          ldiv            = 109,
          fdiv            = 110,
          ddiv            = 111,
          imod            = 112,
          lmod            = 113,
          fmod            = 114,
          dmod            = 115,
          ineg            = 116,
          lneg            = 117,
          fneg            = 118,
          dneg            = 119,
          ishl            = 120,
          lshl            = 121,
          ishr            = 122,
          lshr            = 123,
          iushr           = 124,
          lushr           = 125,
          iand            = 126,
          land            = 127,
          ior             = 128,
          lor             = 129,
          ixor            = 130,
          lxor            = 131,
          iinc            = 132,
          i2l             = 133,
          i2f             = 134,
          i2d             = 135,
          l2i             = 136,
          l2f             = 137,
          l2d             = 138,
          f2i             = 139,
          f2l             = 140,
          f2d             = 141,
          d2i             = 142,
          d2l             = 143,
          d2f             = 144,
          int2byte        = 145,
          int2char        = 146,
          int2short       = 147,
          lcmp            = 148,
          fcmpl           = 149,
          fcmpg           = 150,
          dcmpl           = 151,
          dcmpg           = 152,
          ifeq            = 153,
          ifne            = 154,
          iflt            = 155,
          ifge            = 156,
          ifgt            = 157,
          ifle            = 158,
          if_icmpeq       = 159,
          if_icmpne       = 160,
          if_icmplt       = 161,
          if_icmpge       = 162,
          if_icmpgt       = 163,
          if_icmple       = 164,
          if_acmpeq       = 165,
          if_acmpne       = 166,
          goto_           = 167,
          jsr             = 168,
          ret             = 169,
          tableswitch     = 170,
          lookupswitch    = 171,
          ireturn         = 172,
          lreturn         = 173,
          freturn         = 174,
          dreturn         = 175,
          areturn         = 176,
          return_         = 177,
          getstatic       = 178,
          putstatic       = 179,
          getfield        = 180,
          putfield        = 181,
          invokevirtual   = 182,
          invokespecial   = 183,
          invokestatic    = 184,
          invokeinterface = 185,
          // ___unused___ = 186,
          new_            = 187,
          newarray        = 188,
          anewarray       = 189,
          arraylength     = 190,
          athrow          = 191,
          checkcast       = 192,
          instanceof_     = 193,
          monitorenter    = 194,
          monitorexit     = 195,
          wide            = 196,
          multianewarray  = 197,
          if_acmp_null    = 198,
          if_acmp_nonnull = 199,
          goto_w          = 200,
          jsr_w           = 201,
          breakpoint      = 202,
          ByteCodeCount   = 203;

      /** 
       * Virtual instruction codes; used for constant folding.
       */
      int string_add      = 256,  // string +
          bool_not        = 257,  // boolean !
          bool_and        = 258,  // boolean &&
          bool_or         = 259;  // boolean ||

      /** 
       * Virtual opcodes; used for shifts with long shiftcount
       */
      int ishll           = 270,  // int shift left with long count
          lshll           = 271,  // long shift left with long count
          ishrl           = 272,  // int shift right with long count
          lshrl           = 273,  // long shift right with long count
          iushrl          = 274,  // int unsigned shift right with long count
          lushrl          = 275;  // long unsigned shift right with long count

      /** 
       * Virtual opcode for null reference checks
       */
      int nullchk         = 276;  // return operand if non-null,
                                  // otherwise throw NullPointerException.

      /** Virtual opcode for disallowed operations.
       */
      int error           = 277;

      /** 
       * All conditional jumps come in pairs. To streamline the
       * treatment of jumps, we also introduce a negation of an
       * unconditional jump. That opcode happens to be jsr.
       */
      int dontgoto        = jsr;

      /** 
       * Shift and mask constants for shifting prefix instructions.
       * a pair of instruction codes such as LCMP ; IFEQ is encoded
       * in Symtab as (LCMP << preShift) + IFEQ.
       */
      int preShift        = 9;
      int preMask         = (1 << preShift) - 1;

      /** 
       * Type codes.
       */
      int INTcode         = 0,
          LONGcode        = 1,
          FLOATcode       = 2,
          DOUBLEcode      = 3,
          OBJECTcode      = 4,
          BYTEcode        = 5,
          CHARcode        = 6,
          SHORTcode       = 7,
          VOIDcode        = 8,
          TypeCodeCount   = 9;

      static final String[] typecodeNames = {
          "int",
          "long",
          "float",
          "double",
          "object",
          "byte",
          "char",
          "short",
          "void",
          "oops"
      };
  } // End of constant class //  
  
  /** 
   * A byte buffer is a flexible array which grows when elements are
   * appended. There are also methods to append names to byte buffers
   * and to convert byte buffers to names.   
   */
 public static class ByteBuffer {

     /** An array holding the bytes in this buffer; can be grown.
      */
     public byte[] elems;

     /** The current number of defined bytes in this buffer.
      */
     public int length;

     private String name = "none";
     
     /** Create a new byte buffer.
      */
     public ByteBuffer() {
         this(64);
     }
              
     /** Create a new byte buffer with an initial elements array
      *  of given size.
      */
     public ByteBuffer(int initialSize) {
         elems = new byte[initialSize];
         length = 0;
     }
     
     public ByteBuffer setName(final String n) {
       this.name = n;
       return this;
     }

     private void copy(int size) {
         byte[] newelems = new byte[size];
         System.arraycopy(elems, 0, newelems, 0, elems.length);
         elems = newelems;
     }

     /** Append byte to this buffer.
      */
     public void appendByte(int b) {
         if (length >= elems.length) copy(elems.length * 2);
         elems[length++] = (byte)b;
         System.out.println(name + ": CompilerWriter: Appending to Buffer : appendByte : val=" + b);
     }

     /** Append len bytes from byte array,
      *  starting at given `start' offset.
      */
     public void appendBytes(byte[] bs, int start, int len) {
         while (length + len > elems.length) copy(elems.length * 2);
         System.arraycopy(bs, start, elems, length, len);
         length += len;
     }

     /** 
      * Append all bytes from given byte array.
      */
     public void appendBytes(byte[] bs) {
         appendBytes(bs, 0, bs.length);
     }

     /** Append a character as a two byte number.
      */
     public void appendChar(int x) {
         while (length + 1 >= elems.length) copy(elems.length * 2);
         elems[length  ] = (byte)((x >>  8) & 0xFF);
         elems[length+1] = (byte)((x      ) & 0xFF);
         length = length + 2;
         System.out.println(name + ": CompilerWriter: Appending to Buffer : appendChar : val=" + x);
     }

     /** Append an integer as a four byte number.
      */
     public void appendInt(int x) {
         while (length + 3 >= elems.length) copy(elems.length * 2);
         elems[length  ] = (byte)((x >> 24) & 0xFF);
         elems[length+1] = (byte)((x >> 16) & 0xFF);
         elems[length+2] = (byte)((x >>  8) & 0xFF);
         elems[length+3] = (byte)((x      ) & 0xFF);
         length = length + 4;
         System.out.println(name + ": CompilerWriter: Appending to Buffer : appendInt : val=" + x);
     }

     /** 
      * Append a long as an eight byte number.
      */
     public void appendLong(long x) {
         final ByteArrayOutputStream buffer = new ByteArrayOutputStream(8);
         final DataOutputStream bufout = new DataOutputStream(buffer);
         try {
             bufout.writeLong(x);
             appendBytes(buffer.toByteArray(), 0, 8);
         } catch (IOException e) {
             throw new AssertionError("write");
         }
     }

     /** Append a float as a four byte number.
      */
     public void appendFloat(float x) {
         ByteArrayOutputStream buffer = new ByteArrayOutputStream(4);
         DataOutputStream bufout = new DataOutputStream(buffer);
         try {
             bufout.writeFloat(x);
             appendBytes(buffer.toByteArray(), 0, 4);
         } catch (IOException e) {
             throw new AssertionError("write");
         }
     }

     /** Append a double as a eight byte number.
      */
     public void appendDouble(double x) {
         ByteArrayOutputStream buffer = new ByteArrayOutputStream(8);
         DataOutputStream bufout = new DataOutputStream(buffer);
         try {
             bufout.writeDouble(x);
             appendBytes(buffer.toByteArray(), 0, 8);
         } catch (IOException e) {
             throw new AssertionError("write");
         }
     }

     /** Append a name.
      */
     public void appendName(Name name) {
         appendBytes(name.table.names, name.index, name.len);
     }

     /** Reset to zero length.
      */
     public void reset() {
         length = 0;
     }

     /** Convert contents to name.
      */
     public Name toName(Name.Table names) {
         return names.fromUtf(elems, 0, length);
     }
 } // End of class //
  
  /** 
   * An abstraction for internal compiler strings. For efficiency reasons,
   * GJC uses hashed strings that are stored in a common large buffer.  
   */
  public static class Name {

      /** The table structure where the name is stored
       */
      public Table table;

      /** The index where the bytes of this name are stored in the global name
       *  buffer `names'.
       */
      public int index;

      /** The number of bytes in this name.
       */
      public int len;

      /** The next name occupying the same hash bucket.
       */
      Name next;

      /** The hashcode of a name.
       */
      private static int hashValue(byte cs[], int start, int len) {
          int h = 0;
          int off = start;

          for (int i = 0; i < len; i++) {
              h = (h << 5) - h + cs[off++];
          }
          return h;
      }

      /** Is (the utf8 representation of) name equal to
       *  cs[start..start+len-1]?
       */
      private static boolean equals(byte[] names, int index,
                                    byte cs[], int start, int len) {
          int i = 0;
          while (i < len && names[index + i] == cs[start + i]) i++;
          return i == len;
      }

      /** Create a name from the bytes in cs[start..start+len-1].
       *  Assume that bytes are in utf8 format.
       */
      public static Name fromUtf(Table table, byte cs[], int start, int len) {
          int h = hashValue(cs, start, len) & table.hashMask;
          Name n = table.hashes[h];
          byte[] names = table.names;
          while (n != null &&
                 (n.len != len || !equals(names, n.index, cs, start, len)))
              n = n.next;
          if (n == null) {
              int nc = table.nc;
              while (nc + len > names.length) {
                  byte[] newnames = new byte[names.length * 2];
                  System.arraycopy(names, 0, newnames, 0, names.length);
                  names = table.names = newnames;
              }
              System.arraycopy(cs, start, names, nc, len);
              n = new Name();
              n.table = table;
              n.index = nc;
              n.len = len;
              n.next = table.hashes[h];
              table.hashes[h] = n;
              table.nc = nc + len;
              if (len == 0) table.nc++;
          }
          return n;
      }

      /** Create a name from the bytes in array cs.
       *  Assume that bytes are in utf8 format.
       */
      public static Name fromUtf(Table table, byte cs[]) {
          return fromUtf(table, cs, 0, cs.length);
      }

      /** Create a name from the characters in cs[start..start+len-1].
       */
      public static Name fromChars(Table table, char[] cs, int start, int len) {
          int nc = table.nc;
          byte[] names = table.names;
          while (nc + len * 3 >= names.length) {
              byte[] newnames = new byte[names.length * 2];
              System.arraycopy(names, 0, newnames, 0, names.length);
              names = table.names = newnames;
          }
          int nbytes = Convert.chars2utf(cs, start, names, nc, len) - nc;
          int h = hashValue(names, nc, nbytes) & table.hashMask;
          Name n = table.hashes[h];
          while (n != null &&
                 (n.len != nbytes ||
                  !equals(names, n.index, names, nc, nbytes)))
              n = n.next;
          if (n == null) {
              n = new Name();
              n.table = table;
              n.index = nc;
              n.len = nbytes;
              n.next = table.hashes[h];
              table.hashes[h] = n;
              table.nc = nc + nbytes;
              if (nbytes == 0) table.nc++;
          }
          return n;
      }

      /** Create a name from the characters in string s.
       */
      public static Name fromString(Table table, String s) {
          char[] cs = s.toCharArray();
          return fromChars(table, cs, 0, cs.length);
      }

      /** Create a name from the characters in char sequence s.
       */
      public static Name fromString(Table table, CharSequence s) {
          return fromString(table, s.toString());
      }

      /** Return the Utf8 representation of this name.
       */
      public byte[] toUtf() {
          byte[] bs = new byte[len];
          System.arraycopy(table.names, index, bs, 0, len);
          return bs;
      }

      /** 
       * Return the string representation of this name.
       */
      public String toString() {
          return Convert.utf2string(table.names, index, len);
      }

      /** Copy all bytes of this name to buffer cs, starting at start.
       */
      public void getBytes(byte cs[], int start) {
          System.arraycopy(table.names, index, cs, start, len);
      }

      /** Return the hash value of this name.
       */
      public int hashCode() {
          return index;
      }

      /** Is this name equal to other?
       */
      public boolean equals(Object other) {
          if (other instanceof Name)
              return
                  table == ((Name)other).table && index == ((Name)other).index;
          else return false;
      }

      /** Compare this name to other name, yielding -1 if smaller, 0 if equal,
       *  1 if greater.
       */
      public boolean less(Name that) {
          int i = 0;
          while (i < this.len && i < that.len) {
              byte thisb = this.table.names[this.index + i];
              byte thatb = that.table.names[that.index + i];
              if (thisb < thatb) return true;
              else if (thisb > thatb) return false;
              else i++;
          }
          return this.len < that.len;
      }

      /** Returns the length of this name.
       */
      public int length() {
          return toString().length();
      }

      /** Returns i'th byte of this name.
       */
      public byte byteAt(int i) {
          return table.names[index + i];
      }

      /** Returns first occurrence of byte b in this name, len if not found.
       */
      public int indexOf(byte b) {
          byte[] names = table.names;
          int i = 0;
          while (i < len && names[index + i] != b) i++;
          return i;
      }

      /**
       * Returns last occurrence of byte b in this name, -1 if not found.
       */
      public int lastIndexOf(byte b) {
          byte[] names = table.names;
          int i = len - 1;
          while (i >= 0 && names[index + i] != b) i--;
          return i;
      }

      /** 
       * Does this name start with prefix?
       */
      public boolean startsWith(Name prefix) {
          int i = 0;
          while (i < prefix.len &&
                 i < len &&
                 table.names[index + i] == prefix.table.names[prefix.index + i])
              i++;
          return i == prefix.len;
      }

      /** Does this name end with suffix?
       */
      public boolean endsWith(Name suffix) {
          int i = len - 1;
          int j = suffix.len - 1;
          while (j >= 0 && i >= 0 &&
                 table.names[index + i] == suffix.table.names[suffix.index + j]) {
              i--; j--;
          }
          return j < 0;
      }

      /** Returns the sub-name starting at position start, up to and
       *  excluding position end.
       */
      public Name subName(int start, int end) {
          if (end < start) end = start;
          return fromUtf(table, table.names, index + start, end - start);
      }

      /** 
       * Replace all `from' bytes in this name with `to' bytes.
       */
      public Name replace(byte from, byte to) {
          byte[] names = table.names;
          int i = 0;
          while (i < len) {
              if (names[index + i] == from) {
                  byte[] bs = new byte[len];
                  System.arraycopy(names, index, bs, 0, i);
                  bs[i] = to;
                  i++;
                  while (i < len) {
                      byte b = names[index + i];
                      bs[i] = b == from ? to : b;
                      i++;
                  }
                  return fromUtf(table, bs, 0, len);
              }
              i++;
          }
          return this;
      }

      /** Return the concatenation of this name and name `n'.
       */
      public Name append(Name n) {
          byte[] bs = new byte[len + n.len];
          getBytes(bs, 0);
          n.getBytes(bs, len);
          return fromUtf(table, bs, 0, bs.length);
      }

      /** 
       * Return the concatenation of this name, the given ASCII
       * character, and name `n'.
       */
      public Name append(char c, Name n) {
          byte[] bs = new byte[len + n.len + 1];
          getBytes(bs, 0);
          bs[len] = (byte)c;
          n.getBytes(bs, len+1);
          return fromUtf(table, bs, 0, bs.length);
      }

      /** 
       * An arbitrary but consistent complete order among all Names.
       */
      public int compareTo(Name other) {
          return other.index - this.index;
      }

      /** 
       * Return the concatenation of all names in the array `ns'.
       */
      public static Name concat(Table table, Name ns[]) {
          int len = 0;
          for (int i = 0; i < ns.length; i++)
              len = len + ns[i].len;
          byte[] bs = new byte[len];
          len = 0;
          for (int i = 0; i < ns.length; i++) {
              ns[i].getBytes(bs, len);
              len = len + ns[i].len;
          }
          return fromUtf(table, bs, 0, len);
      }

      public char charAt(int index) {
          return toString().charAt(index);
      }

      public CharSequence subSequence(int start, int end) {
          return toString().subSequence(start, end);
      }

      public boolean contentEquals(CharSequence cs) {
          return this.toString().equals(cs.toString());
      }

      public static class Table {
        
          /** The hash table for names.
           */
          private Name[] hashes;

          /** The array holding all encountered names.
           */
          public byte[] names;

          /** The mask to be used for hashing
           */
          private int hashMask;

          /** The number of filled bytes in `names'.
           */
          private int nc = 0;
          
          public static synchronized Table make() {              
              return new Table();
          }

          public Table(int hashSize, int nameSize) {
              hashMask = hashSize - 1;
              hashes = new Name[hashSize];
              names = new byte[nameSize];

              slash = fromString("/");
              hyphen = fromString("-");
              T = fromString("T");
              slashequals = fromString("/=");
              deprecated = fromString("deprecated");

              init = fromString("<init>");
              clinit = fromString("<clinit>");
              error = fromString("<error>");
              any = fromString("<any>");
              empty = fromString("");
              one = fromString("1");
              period = fromString(".");
              comma = fromString(",");
              semicolon = fromString(";");
              asterisk = fromString("*");
              _this = fromString("this");
              _super = fromString("super");
              _default = fromString("default");

              _class = fromString("class");
              java_lang = fromString("java.lang");
              java_lang_Object = fromString("java.lang.Object");
              java_lang_Class = fromString("java.lang.Class");
              java_lang_Cloneable = fromString("java.lang.Cloneable");
              java_io_Serializable = fromString("java.io.Serializable");
              java_lang_Enum = fromString("java.lang.Enum");
              package_info = fromString("package-info");
              serialVersionUID = fromString("serialVersionUID");
              ConstantValue = fromString("ConstantValue");
              LineNumberTable = fromString("LineNumberTable");
              LocalVariableTable = fromString("LocalVariableTable");
              LocalVariableTypeTable = fromString("LocalVariableTypeTable");
              CharacterRangeTable = fromString("CharacterRangeTable");
              StackMap = fromString("StackMap");
              StackMapTable = fromString("StackMapTable");
              SourceID = fromString("SourceID");
              CompilationID = fromString("CompilationID");
              Code = fromString("Code");
              Exceptions = fromString("Exceptions");
              SourceFile = fromString("SourceFile");
              InnerClasses = fromString("InnerClasses");
              Synthetic = fromString("Synthetic");
              Bridge= fromString("Bridge");
              Deprecated = fromString("Deprecated");
              Enum = fromString("Enum");
              _name = fromString("name");
              Signature = fromString("Signature");
              Varargs = fromString("Varargs");
              Annotation = fromString("Annotation");
              RuntimeVisibleAnnotations = fromString("RuntimeVisibleAnnotations");
              RuntimeInvisibleAnnotations = fromString("RuntimeInvisibleAnnotations");
              RuntimeVisibleParameterAnnotations = fromString("RuntimeVisibleParameterAnnotations");
              RuntimeInvisibleParameterAnnotations = fromString("RuntimeInvisibleParameterAnnotations");
              Value = fromString("Value");
              EnclosingMethod = fromString("EnclosingMethod");

              desiredAssertionStatus = fromString("desiredAssertionStatus");

              append  = fromString("append");
              family  = fromString("family");
              forName = fromString("forName");
              toString = fromString("toString");
              length = fromString("length");
              valueOf = fromString("valueOf");
              value = fromString("value");
              getMessage = fromString("getMessage");
              getClass = fromString("getClass");

              TYPE = fromString("TYPE");
              FIELD = fromString("FIELD");
              METHOD = fromString("METHOD");
              PARAMETER = fromString("PARAMETER");
              CONSTRUCTOR = fromString("CONSTRUCTOR");
              LOCAL_VARIABLE = fromString("LOCAL_VARIABLE");
              ANNOTATION_TYPE = fromString("ANNOTATION_TYPE");
              PACKAGE = fromString("PACKAGE");

              SOURCE = fromString("SOURCE");
              CLASS = fromString("CLASS");
              RUNTIME = fromString("RUNTIME");

              Array = fromString("Array");
              Method = fromString("Method");
              Bound = fromString("Bound");
              clone = fromString("clone");
              getComponentType = fromString("getComponentType");
              getClassLoader = fromString("getClassLoader");
              initCause = fromString("initCause");
              values = fromString("values");
              iterator = fromString("iterator");
              hasNext = fromString("hasNext");
              next = fromString("next");
              AnnotationDefault = fromString("AnnotationDefault");
              ordinal = fromString("ordinal");
              equals = fromString("equals");
              hashCode = fromString("hashCode");
              compareTo = fromString("compareTo");
              getDeclaringClass = fromString("getDeclaringClass");
              ex = fromString("ex");
              finalize = fromString("finalize");
          }

          public Table() {
              this(0x8000, 0x20000);
          }

          /** Create a name from the bytes in cs[start..start+len-1].
           *  Assume that bytes are in utf8 format.
           */
          public Name fromUtf(byte cs[], int start, int len) {
              return Name.fromUtf(this, cs, start, len);
          }

          /** Create a name from the bytes in array cs.
           *  Assume that bytes are in utf8 format.
           */
          public Name fromUtf(byte cs[]) {
              return Name.fromUtf(this, cs, 0, cs.length);
          }

          /** Create a name from the characters in cs[start..start+len-1].
           */
          public Name fromChars(char[] cs, int start, int len) {
              return Name.fromChars(this, cs, start, len);
          }

          /** Create a name from the characters in string s.
           */
          public Name fromString(CharSequence s) {
              return Name.fromString(this, s);
          }

          public final Name slash;
          public final Name hyphen;
          public final Name T;
          public final Name slashequals;
          public final Name deprecated;

          public final Name init;
          public final Name clinit;
          public final Name error;
          public final Name any;
          public final Name empty;
          public final Name one;
          public final Name period;
          public final Name comma;
          public final Name semicolon;
          public final Name asterisk;
          public final Name _this;
          public final Name _super;
          public final Name _default;

          public final Name _class;
          public final Name java_lang;
          public final Name java_lang_Object;
          public final Name java_lang_Class;
          public final Name java_lang_Cloneable;
          public final Name java_io_Serializable;
          public final Name serialVersionUID;
          public final Name java_lang_Enum;
          public final Name package_info;
          public final Name ConstantValue;
          public final Name LineNumberTable;
          public final Name LocalVariableTable;
          public final Name LocalVariableTypeTable;
          public final Name CharacterRangeTable;
          public final Name StackMap;
          public final Name StackMapTable;
          public final Name SourceID;
          public final Name CompilationID;
          public final Name Code;
          public final Name Exceptions;
          public final Name SourceFile;
          public final Name InnerClasses;
          public final Name Synthetic;
          public final Name Bridge;
          public final Name Deprecated;
          public final Name Enum;
          public final Name _name;
          public final Name Signature;
          public final Name Varargs;
          public final Name Annotation;
          public final Name RuntimeVisibleAnnotations;
          public final Name RuntimeInvisibleAnnotations;
          public final Name RuntimeVisibleParameterAnnotations;
          public final Name RuntimeInvisibleParameterAnnotations;

          public final Name Value;
          public final Name EnclosingMethod;

          public final Name desiredAssertionStatus;

          public final Name append;
          public final Name family;
          public final Name forName;
          public final Name toString;
          public final Name length;
          public final Name valueOf;
          public final Name value;
          public final Name getMessage;
          public final Name getClass;

          public final Name TYPE;
          public final Name FIELD;
          public final Name METHOD;
          public final Name PARAMETER;
          public final Name CONSTRUCTOR;
          public final Name LOCAL_VARIABLE;
          public final Name ANNOTATION_TYPE;
          public final Name PACKAGE;

          public final Name SOURCE;
          public final Name CLASS;
          public final Name RUNTIME;

          public final Name Array;
          public final Name Method;
          public final Name Bound;
          public final Name clone;
          public final Name getComponentType;
          public final Name getClassLoader;
          public final Name initCause;
          public final Name values;
          public final Name iterator;
          public final Name hasNext;
          public final Name next;
          public final Name AnnotationDefault;
          public final Name ordinal;
          public final Name equals;
          public final Name hashCode;
          public final Name compareTo;
          public final Name getDeclaringClass;
          public final Name ex;
          public final Name finalize;
      }

      public boolean isEmpty() {
          return len == 0;
      }
  }
  
  /** 
   * Utility class for static conversion methods between numbers
   * and strings in various formats.
   */
  public static class Convert {

      /** Convert string to integer.
       */
      public static int string2int(String s, int radix)
          throws NumberFormatException {
          if (radix == 10) {
              return Integer.parseInt(s, radix);
          } else {
              char[] cs = s.toCharArray();
              int limit = Integer.MAX_VALUE / (radix/2);
              int n = 0;
              for (int i = 0; i < cs.length; i++) {
                  int d = Character.digit(cs[i], radix);
                  if (n < 0 ||
                      n > limit ||
                      n * radix > Integer.MAX_VALUE - d)
                      throw new NumberFormatException();
                  n = n * radix + d;
              }
              return n;
          }
      }

      /** Convert string to long integer.
       */
      public static long string2long(String s, int radix)
          throws NumberFormatException {
          if (radix == 10) {
              return Long.parseLong(s, radix);
          } else {
              char[] cs = s.toCharArray();
              long limit = Long.MAX_VALUE / (radix/2);
              long n = 0;
              for (int i = 0; i < cs.length; i++) {
                  int d = Character.digit(cs[i], radix);
                  if (n < 0 ||
                      n > limit ||
                      n * radix > Long.MAX_VALUE - d)
                      throw new NumberFormatException();
                  n = n * radix + d;
              }
              return n;
          }
      }

      public static int utf2chars(byte[] src, int sindex,
                                  char[] dst, int dindex,
                                  int len) {
          int i = sindex;
          int j = dindex;
          int limit = sindex + len;
          while (i < limit) {
              int b = src[i++] & 0xFF;
              if (b >= 0xE0) {
                  b = (b & 0x0F) << 12;
                  b = b | (src[i++] & 0x3F) << 6;
                  b = b | (src[i++] & 0x3F);
              } else if (b >= 0xC0) {
                  b = (b & 0x1F) << 6;
                  b = b | (src[i++] & 0x3F);
              }
              dst[j++] = (char)b;
          }
          return j;
      }

      public static char[] utf2chars(byte[] src, int sindex, int len) {
          char[] dst = new char[len];
          int len1 = utf2chars(src, sindex, dst, 0, len);
          char[] result = new char[len1];
          System.arraycopy(dst, 0, result, 0, len1);
          return result;
      }

      public static char[] utf2chars(byte[] src) {
          return utf2chars(src, 0, src.length);
      }

      public static String utf2string(byte[] src, int sindex, int len) {
          char dst[] = new char[len];
          int len1 = utf2chars(src, sindex, dst, 0, len);
          return new String(dst, 0, len1);
      }

      public static String utf2string(byte[] src) {
          return utf2string(src, 0, src.length);
      }

      public static int chars2utf(char[] src, int sindex,
                                  byte[] dst, int dindex,
                                  int len) {
          int j = dindex;
          int limit = sindex + len;
          for (int i = sindex; i < limit; i++) {
              char ch = src[i];
              if (1 <= ch && ch <= 0x7F) {
                  dst[j++] = (byte)ch;
              } else if (ch <= 0x7FF) {
                  dst[j++] = (byte)(0xC0 | (ch >> 6));
                  dst[j++] = (byte)(0x80 | (ch & 0x3F));
              } else {
                  dst[j++] = (byte)(0xE0 | (ch >> 12));
                  dst[j++] = (byte)(0x80 | ((ch >> 6) & 0x3F));
                  dst[j++] = (byte)(0x80 | (ch & 0x3F));
              }
          }
          return j;
      }
      
      public static byte[] chars2utf(char[] src, int sindex, int len) {
          byte[] dst = new byte[len * 3];
          int len1 = chars2utf(src, sindex, dst, 0, len);
          byte[] result = new byte[len1];
          System.arraycopy(dst, 0, result, 0, len1);
          return result;
      }

      
      public static byte[] chars2utf(char[] src) {
          return chars2utf(src, 0, src.length);
      }

      /** Return string as an array of bytes in in Utf8 representation.
       */
      public static byte[] string2utf(String s) {
          return chars2utf(s.toCharArray());
      }

      /**
       * Escapes each character in a string that has an escape sequence or
       * is non-printable ASCII.  Leaves non-ASCII characters alone.
       */
      public static String quote(String s) {
          StringBuilder buf = new StringBuilder();
          for (int i = 0; i < s.length(); i++) {
              buf.append(quote(s.charAt(i)));
          }
          return buf.toString();
      }

      /**
       * Escapes a character if it has an escape sequence or is
       * non-printable ASCII.  Leaves non-ASCII characters alone.
       */
      public static String quote(char ch) {
          switch (ch) {
          case '\b':  return "\\b";
          case '\f':  return "\\f";
          case '\n':  return "\\n";
          case '\r':  return "\\r";
          case '\t':  return "\\t";
          case '\'':  return "\\'";
          case '\"':  return "\\\"";
          case '\\':  return "\\\\";
          default:
              return (isPrintableAscii(ch))
                  ? String.valueOf(ch)
                  : String.format("\\u%04x", (int) ch);
          }
      }

      /**
       * Is a character printable ASCII?
       */
      private static boolean isPrintableAscii(char ch) {
          return ch >= ' ' && ch <= '~';
      }

      /** 
       * Escape all unicode characters in string.
       */
      public static String escapeUnicode(String s) {
          int len = s.length();
          int i = 0;
          while (i < len) {
              char ch = s.charAt(i);
              if (ch > 255) {
                  StringBuffer buf = new StringBuffer();
                  buf.append(s.substring(0, i));
                  while (i < len) {
                      ch = s.charAt(i);
                      if (ch > 255) {
                          buf.append("\\u");
                          buf.append(Character.forDigit((ch >> 12) % 16, 16));
                          buf.append(Character.forDigit((ch >>  8) % 16, 16));
                          buf.append(Character.forDigit((ch >>  4) % 16, 16));
                          buf.append(Character.forDigit((ch      ) % 16, 16));
                      } else {
                          buf.append(ch);
                      }
                      i++;
                  }
                  s = buf.toString();
              } else {
                  i++;
              }
          }
          return s;
      }
      public static Name shortName(Name classname) {
          return classname.subName(
              classname.lastIndexOf((byte)'.') + 1, classname.len);
      }

      public static String shortName(String classname) {
          return classname.substring(classname.lastIndexOf('.') + 1);
      }

      public static Name packagePart(Name classname) {
          return classname.subName(0, classname.lastIndexOf((byte)'.'));
      }

      public static String packagePart(String classname) {
          int lastDot = classname.lastIndexOf('.');
          return (lastDot < 0 ? "" : classname.substring(0, lastDot));
      }    
  }  
    
  /** 
   * An internal structure that corresponds to the constant pool of a classfile.   
   */
 public static class Pool {

     public static final int MAX_ENTRIES = 0xFFFF;
     public static final int MAX_STRING_LENGTH = 0xFFFF;

     /** 
      * Index of next constant to be entered.
      */
     int pp;

     /** The initial pool buffer.
      */
     Object[] pool;

     /** 
      * A hashtable containing all constants in the pool.
      */
     Map<Object,Integer> indices;

     /** Construct a pool with given number of elements and element array.
      */
     public Pool(int pp, Object[] pool) {
         this.pp = pp;
         this.pool = pool;
         this.indices = new HashMap<Object,Integer>(pool.length);
         for (int i = 1; i < pp; i++) {
             if (pool[i] != null) indices.put(pool[i], i);
         }
     }

     /** Construct an empty pool.
      */
     public Pool() {
         this(1, new Object[64]);
     }

     /** Return the number of entries in the constant pool.
      */
     public int numEntries() {
         return pp;
     }

     /** Remove everything from this pool.
      */
     public void reset() {
         pp = 1;
         indices.clear();
     }

     /** Double pool buffer in size.
      */
     private void doublePool() {
         Object[] newpool = new Object[pool.length * 2];
         System.arraycopy(pool, 0, newpool, 0, pool.length);
         pool = newpool;
     }

     /** 
      * Place an object in the pool, unless it is already there.
      * If object is a symbol also enter its owner unless the owner is a
      * package.  Return the object's index in the pool.
      */
     public int put(final Object value) {
                
         Integer index = indices.get(value);
         if (index == null) {
             index = pp;
             indices.put(value, index);
             if (pp == pool.length) doublePool();
             pool[pp++] = value;
             if (value instanceof Long || value instanceof Double) {
                 if (pp == pool.length) doublePool();
                 pool[pp++] = null;
             }
         }
         return index.intValue();
     }

     /** Return the given object's index in the pool,
      *  or -1 if object is not in there.
      */
     public int get(Object o) {
         Integer n = indices.get(o);
         return n == null ? -1 : n.intValue();
     }    
 } // End of Pool Class //
 
 /** 
  * Root class for Java symbols. It contains subclasses
  * for specific sorts of symbols, such as variables, methods and operators,
  * types, packages. Each subclass is represented as a static inner class
  * inside Symbol.  
  */
 public abstract static class Symbol implements Element {
   
     /** The kind of this symbol.
      *  @see Kinds
      */
     public int kind;

     /** The flags of this symbol.
      */
     public long flags_field;

     /** An accessor method for the flags of this symbol.
      *  Flags of class symbols should be accessed through the accessor
      *  method to make sure that the class symbol is loaded.
      */
     public long flags() { return flags_field; }   

     /** The name of this symbol in Utf8 representation.
      */
     public Name name;
     
     public Symbol owner;
    
     /** Construct a symbol with given kind, flags, name, type and owner.
      */
     public Symbol(int kind, long flags, Name name, Symbol owner) {
         this.kind = kind;
         this.flags_field = flags;         
         this.owner = owner;                           
         this.name = name;
     }

     /** Clone this symbol with new owner.
      *  Legal only for fields and methods.
      */
     public Symbol clone(Symbol newOwner) {
         throw new AssertionError();
     }

     /** The Java source which this symbol represents.
      *  A description of this symbol; overrides Object.
      */
     public String toString() {
         return name.toString();
     }
     
     public boolean isStatic() {
         return
             (flags() & Flags.STATIC) != 0 ||
             (owner.flags() & Flags.INTERFACE) != 0 && kind != Flags.MTH;
     }

     public boolean isInterface() {
         return (flags() & Flags.INTERFACE) != 0;
     }

     /** Is this symbol declared (directly or indirectly) local
      *  to a method or variable initializer?
      *  Also includes fields of inner classes which are in
      *  turn local to a method or variable initializer.
      */
     public boolean isLocal() {
         return
             (owner.kind & (Flags.VAR | Flags.MTH)) != 0 ||
             (owner.kind == Flags.TYP && owner.isLocal());
     }

     /** Is this symbol a constructor?
      */
     public boolean isConstructor() {
         return name == name.table.init;
     }

     /** The fully qualified name of this symbol.
      *  This is the same as the symbol's name except for class symbols,
      *  which are handled separately.
      */
     public Name getQualifiedName() {
         return name;
     }

     /** The fully qualified name of this symbol after converting to flat
      *  representation. This is the same as the symbol's name except for
      *  class symbols, which are handled separately.
      */
     public Name flatName() {
         return getQualifiedName();
     }

     /** The closest enclosing class of this symbol's declaration.
      */
     public ClassSymbol enclClass() {
         Symbol c = this;
         while (c != null &&
                ((c.kind & Flags.TYP) == 0)) {
             c = c.owner;
         }
         return (ClassSymbol)c;
     }

     /** The outermost class which indirectly owns this symbol.
      */
     public ClassSymbol outermostClass() {
         Symbol sym = this;
         Symbol prev = null;
         while (sym.kind != Flags.PCK) {
             prev = sym;
             sym = sym.owner;
         }
         return (ClassSymbol) prev;
     }

     /** The package which indirectly owns this symbol.
      */
     public PackageSymbol packge() {
         Symbol sym = this;
         while (sym.kind != Flags.PCK) {
             sym = sym.owner;
         }
         return (PackageSymbol) sym;
     }
             
     public boolean isEnclosedBy(ClassSymbol clazz) {
         for (Symbol sym = this; sym.kind != Flags.PCK; sym = sym.owner)
             if (sym == clazz) return true;
         return false;
     }

    
     /** True if the symbol represents an entity that exists.
      */
     public boolean exists() {
         return true;
     }
   
     public Symbol getEnclosingElement() {
         return owner;
     }
   
     public Name getSimpleName() {
         return name;
     }
        
     public static class DelegatedSymbol extends Symbol {
         protected Symbol other;
         public DelegatedSymbol(Symbol other) {
             super(other.kind, other.flags_field, other.name, other.owner);
             this.other = other;
         }
         public String toString() { return other.toString(); }                                    
         public boolean isLocal() { return other.isLocal(); }
         public boolean isConstructor() { return other.isConstructor(); }
         public Name getQualifiedName() { return other.getQualifiedName(); }
         public Name flatName() { return other.flatName(); }        
         public ClassSymbol enclClass() { return other.enclClass(); }
         public ClassSymbol outermostClass() { return other.outermostClass(); }
         public PackageSymbol packge() { return other.packge(); }         
         public boolean isEnclosedBy(ClassSymbol clazz) { return other.isEnclosedBy(clazz); }
         
        @Override
        public List<? extends Element> getEnclosedElements() {        
          return null;
        }         

     }

     /** A class for type symbols. Type variables are represented by instances
      *  of this class, classes and packages by instances of subclasses.
      */
     public static class TypeSymbol extends Symbol {

         public TypeSymbol(long flags, Name name, Symbol owner) {
             super(Flags.TYP, flags, name, owner);
         }

         /** form a fully qualified name from a name and an owner
          */
         static public Name formFullName(Name name, Symbol owner) {
             if (owner == null) return name;
             if (((owner.kind != Flags.ERR)) &&
                 ((owner.kind & (Flags.VAR | Flags.MTH)) != 0
                  || (owner.kind == Flags.TYP)
                  )) return name;
             Name prefix = owner.getQualifiedName();
             if (prefix == null || prefix == prefix.table.empty)
                 return name;
             else return prefix.append('.', name);
         }

         /** form a fully qualified name from a name and an owner, after
          *  converting to flat representation
          */
         static public Name formFlatName(Name name, Symbol owner) {
             if (owner == null ||
                 (owner.kind & (Flags.VAR | Flags.MTH)) != 0
                 ) return name;
             char sep = owner.kind == Flags.TYP ? '$' : '.';
             Name prefix = owner.flatName();
             if (prefix == null || prefix == prefix.table.empty)
                 return name;
             else return prefix.append(sep, name);
         }
        
         public Symbol getGenericElement() {
             return owner;
         }

        @Override
        public List<? extends Element> getEnclosedElements() {
          return null;
        }
                 
     }

     /** A class for package symbols
      */
     public static class PackageSymbol extends TypeSymbol {
         
         public Name fullname;
         public ClassSymbol package_info; // see bug 6443073

         public PackageSymbol(Name name, Symbol owner) {
             super(0, name, owner);
             this.kind = Flags.PCK;             
             this.fullname = formFullName(name, owner);
         }        

         public String toString() {
             return fullname.toString();
         }

         public Name getQualifiedName() {
             return fullname;
         }

         public boolean isUnnamed() {
             return name.isEmpty() && owner != null;
         }        
        
         /** A package "exists" if a type or package that exists has
          *  been seen within it.
          */
         public boolean exists() {
             return (flags_field & Flags.EXISTS) != 0;
         }

         public Symbol getEnclosingElement() {
             return null;
         }

     }

     /** A class for class symbols
      */
     public static class ClassSymbol extends TypeSymbol {

         /** the fully qualified name of the class, i.e. pck.outer.inner.
          *  null for anonymous classes
          */
         public Name fullname;

         /** the fully qualified name of the class after converting to flat
          *  representation, i.e. pck.outer$inner,
          *  set externally for local and anonymous classes
          */
         public Name flatname;

         /** the constant pool of the class
          */
         public Pool pool;

         public ClassSymbol(long flags, Name name, Symbol owner) {
             super(flags, name, owner);             
             this.fullname = formFullName(name, owner);
             this.flatname = formFlatName(name, owner);             
             this.pool = null;
         }
         
         /** The Java source which this symbol represents.
          */
         public String toString() {
             return className();
         }

         public long flags() {          
             return flags_field;
         }

         public String className() {             
           return fullname.toString();
         }

         public Name getQualifiedName() {
             return fullname;
         }

         public Name flatName() {
             return flatname;
         }        
     }

     /** A class for variable symbols
      */
     public static class VarSymbol extends Symbol {

         /** The variable's declaration position.
          */
         public int pos = 0;

         public int adr = -1;

         /** Construct a variable symbol, given its flags, name, type and owner.
          */
         public VarSymbol(long flags, Name name, Symbol owner) {
             super(Flags.VAR, flags, name, owner);
         }

         /** Clone this symbol with new owner.
          */
         public VarSymbol clone(Symbol newOwner) {
             VarSymbol v = new VarSymbol(flags_field, name, newOwner);
             v.pos = pos;
             v.adr = adr;
             v.data = data;
             return v;
         }

         public String toString() {
             return name.toString();
         }

         private Object data;

         public void setData(Object data) {             
             this.data = data;
         }

        @Override
        public List<? extends Element> getEnclosedElements() {      
          return null;
        }
     }

     /** A class for method symbols.
      */
     public static class MethodSymbol extends Symbol {
         
         /** The parameters of the method. */
         public List<VarSymbol> params = null;

         /** The names of the parameters */
         public List<Name> savedParameterNames;

         /** Construct a method symbol, given its flags, name, type and owner.
          */
         public MethodSymbol(long flags, Name name, Symbol owner) {
             super(Flags.MTH, flags, name, owner);             
         }

         /** Clone this symbol with new owner.
          */
         public MethodSymbol clone(Symbol newOwner) {
             MethodSymbol m = new MethodSymbol(flags_field, name, newOwner);             
             return m;
         }

         /** The Java source which this symbol represents.
          */
         public String toString() {            
           return owner.name.toString();
         }              
         private boolean isOverridableIn(TypeSymbol origin) {
             // JLS3 8.4.6.1
             switch ((int)(flags_field & Flags.AccessFlags)) {
             case Flags.PRIVATE:
                 return false;
             case Flags.PUBLIC:
                 return true;
             case Flags.PROTECTED:
                 return (origin.flags() & Flags.INTERFACE) == 0;
             case 0:
                 // for package private: can only override in the same
                 // package
                 return
                     this.packge() == origin.packge() &&
                     (origin.flags() & Flags.INTERFACE) == 0;
             default:
                 return false;
             }
         }
         
         private Name createArgName(int index, List<Name> exclude) {
             String prefix = "arg";
             while (true) {
                 Name argName = name.table.fromString(prefix + index);
                 if (!exclude.contains(argName))
                     return argName;
                 prefix += "$";
             }
         }

         public boolean isVarArgs() {
             return (flags() & Flags.VARARGS) != 0;
         }

        @Override
        public List<? extends Element> getEnclosedElements() {
          return null;
        }

     }

     /** A class for predefined operators.
      */
     public static class OperatorSymbol extends MethodSymbol {
         public int opcode;
         public OperatorSymbol(Name name, int opcode, Symbol owner) {
             super(Flags.PUBLIC | Flags.STATIC, name, owner);
             this.opcode = opcode;
         }
     }
    
 } // End of the class //
 
 public static interface Element {
     
   /**
    * Returns the simple (unqualified) name of this element.
    * The name of a generic type does not include any reference
    * to its formal type parameters.
    */
   Name getSimpleName();

   /**
    * Returns the innermost element
    * within which this element is, loosely speaking, enclosed.
    */
   Element getEnclosingElement();

   /**
    * Returns the elements that are, loosely speaking, directly
    * enclosed by this element.   
    */
   List<? extends Element> getEnclosedElements();

   
   boolean equals(Object obj);
  
   int hashCode();
 } 
 
 public static class Flags {

   private Flags() {}

   public static String toString(long flags) {
       StringBuffer buf = new StringBuffer();
       if ((flags&PUBLIC) != 0) buf.append("public ");
       if ((flags&PRIVATE) != 0) buf.append("private ");
       if ((flags&PROTECTED) != 0) buf.append("protected ");
       if ((flags&STATIC) != 0) buf.append("static ");
       if ((flags&FINAL) != 0) buf.append("final ");
       if ((flags&SYNCHRONIZED) != 0) buf.append("synchronized ");
       if ((flags&VOLATILE) != 0) buf.append("volatile ");
       if ((flags&TRANSIENT) != 0) buf.append("transient ");
       if ((flags&NATIVE) != 0) buf.append("native ");
       if ((flags&INTERFACE) != 0) buf.append("interface ");
       if ((flags&ABSTRACT) != 0) buf.append("abstract ");
       if ((flags&STRICTFP) != 0) buf.append("strictfp ");
       if ((flags&BRIDGE) != 0) buf.append("bridge ");
       if ((flags&SYNTHETIC) != 0) buf.append("synthetic ");
       if ((flags&DEPRECATED) != 0) buf.append("deprecated ");
       if ((flags&HASINIT) != 0) buf.append("hasinit ");
       if ((flags&ENUM) != 0) buf.append("enum ");
       if ((flags&IPROXY) != 0) buf.append("iproxy ");
       if ((flags&NOOUTERTHIS) != 0) buf.append("noouterthis ");
       if ((flags&EXISTS) != 0) buf.append("exists ");
       if ((flags&COMPOUND) != 0) buf.append("compound ");
       if ((flags&CLASS_SEEN) != 0) buf.append("class_seen ");
       if ((flags&SOURCE_SEEN) != 0) buf.append("source_seen ");
       if ((flags&LOCKED) != 0) buf.append("locked ");
       if ((flags&UNATTRIBUTED) != 0) buf.append("unattributed ");
       if ((flags&ANONCONSTR) != 0) buf.append("anonconstr ");
       if ((flags&ACYCLIC) != 0) buf.append("acyclic ");
       if ((flags&PARAMETER) != 0) buf.append("parameter ");
       if ((flags&VARARGS) != 0) buf.append("varargs ");
       return buf.toString();
   }

   /* Standard Java flags.
    */
   public static final int PUBLIC       = 1<<0;
   public static final int PRIVATE      = 1<<1;
   public static final int PROTECTED    = 1<<2;
   public static final int STATIC       = 1<<3;
   public static final int FINAL        = 1<<4;
   public static final int SYNCHRONIZED = 1<<5;
   public static final int VOLATILE     = 1<<6;
   public static final int TRANSIENT    = 1<<7;
   public static final int NATIVE       = 1<<8;
   public static final int INTERFACE    = 1<<9;
   public static final int ABSTRACT     = 1<<10;
   public static final int STRICTFP     = 1<<11;

   /* Flag that marks a symbol synthetic, added in classfile v49.0. */
   public static final int SYNTHETIC    = 1<<12;

   /** Flag that marks attribute interfaces, added in classfile v49.0. */
   public static final int ANNOTATION   = 1<<13;

   /** An enumeration type or an enumeration constant, added in
    *  classfile v49.0. */
   public static final int ENUM         = 1<<14;

   public static final int StandardFlags = 0x0fff;

   // Because the following access flags are overloaded with other
   // bit positions, we translate them when reading and writing class
   // files into unique bits positions: ACC_SYNTHETIC <-> SYNTHETIC,
   // for example.
   public static final int ACC_SUPER    = 0x0020;
   public static final int ACC_BRIDGE   = 0x0040;
   public static final int ACC_VARARGS  = 0x0080;

   /*****************************************
    * Internal compiler flags (no bits in the lower 16).
    *****************************************/

   /** Flag is set if symbol is deprecated.
    */
   public static final int DEPRECATED   = 1<<17;

   /** Flag is set for a variable symbol if the variable's definition
    *  has an initializer part.
    */
   public static final int HASINIT          = 1<<18;

   /** Flag is set for compiler-generated anonymous method symbols
    *  that `own' an initializer block.
    */
   public static final int BLOCK            = 1<<20;

   /** Flag is set for compiler-generated abstract methods that implement
    *  an interface method (Miranda methods).
    */
   public static final int IPROXY           = 1<<21;

   /** Flag is set for nested classes that do not access instance members
    *  or 'this' of an outer class and therefore don't need to be passed
    *  a this$n reference.  This flag is currently set only for anonymous
    *  classes in superclass constructor calls and only for pre 1.4 targets.
    *  todo: use this flag for optimizing away this$n parameters in
    *  other cases.
    */
   public static final int NOOUTERTHIS  = 1<<22;

   /** Flag is set for package symbols if a package has a member or
    *  directory and therefore exists.
    */
   public static final int EXISTS           = 1<<23;

   /** Flag is set for compiler-generated compound classes
    *  representing multiple variable bounds
    */
   public static final int COMPOUND     = 1<<24;

   /** Flag is set for class symbols if a class file was found for this class.
    */
   public static final int CLASS_SEEN   = 1<<25;

   /** Flag is set for class symbols if a source file was found for this
    *  class.
    */
   public static final int SOURCE_SEEN  = 1<<26;

   /* State flags (are reset during compilation).
    */

   /** Flag for class symbols is set and later re-set as a lock in
    *  Enter to detect cycles in the superclass/superinterface
    *  relations.  Similarly for constructor call cycle detection in
    *  Attr.
    */
   public static final int LOCKED           = 1<<27;

   /** Flag for class symbols is set and later re-set to indicate that a class
    *  has been entered but has not yet been attributed.
    */
   public static final int UNATTRIBUTED = 1<<28;

   /** Flag for synthesized default constructors of anonymous classes.
    */
   public static final int ANONCONSTR   = 1<<29;

   /** Flag for class symbols to indicate it has been checked and found
    *  acyclic.
    */
   public static final int ACYCLIC          = 1<<30;

   /** Flag that marks bridge methods.
    */
   public static final long BRIDGE          = 1L<<31;

   /** Flag that marks formal parameters.
    */
   public static final long PARAMETER   = 1L<<33;

   /** Flag that marks varargs methods.
    */
   public static final long VARARGS   = 1L<<34;

   /** Flag for annotation type symbols to indicate it has been
    *  checked and found acyclic.
    */
   public static final long ACYCLIC_ANN      = 1L<<35;

   /** Flag that marks a generated default constructor.
    */
   public static final long GENERATEDCONSTR   = 1L<<36;

   /** Flag that marks a hypothetical method that need not really be
    *  generated in the binary, but is present in the symbol table to
    *  simplify checking for erasure clashes.
    */
   public static final long HYPOTHETICAL   = 1L<<37;

   /**
    * Flag that marks an internal proprietary class.
    */
   public static final long PROPRIETARY = 1L<<38;

   /** Modifier masks.
    */
   public static final int
       AccessFlags           = PUBLIC | PROTECTED | PRIVATE,
       LocalClassFlags       = FINAL | ABSTRACT | STRICTFP | ENUM | SYNTHETIC,
       MemberClassFlags      = LocalClassFlags | INTERFACE | AccessFlags,
       ClassFlags            = LocalClassFlags | INTERFACE | PUBLIC | ANNOTATION,
       InterfaceVarFlags     = FINAL | STATIC | PUBLIC,
       VarFlags              = AccessFlags | FINAL | STATIC |
                               VOLATILE | TRANSIENT | ENUM,
       ConstructorFlags      = AccessFlags,
       InterfaceMethodFlags  = ABSTRACT | PUBLIC,
       MethodFlags           = AccessFlags | ABSTRACT | STATIC | NATIVE |
                               SYNCHRONIZED | FINAL | STRICTFP;
   public static final long
       LocalVarFlags         = FINAL | PARAMETER;
   
      public static boolean isStatic(Symbol symbol) {
       return (symbol.flags() & STATIC) != 0;
   }

   public static boolean isEnum(Symbol symbol) {
       return (symbol.flags() & ENUM) != 0;
   }
   

   /** The empty set of kinds.
    */
   public final static int NIL = 0;

   /** The kind of package symbols.
    */
   public final static int PCK = 1 << 0;

   /** The kind of type symbols (classes, interfaces and type variables).
    */
   public final static int TYP = 1 << 1;

   /** The kind of variable symbols.
    */
   public final static int VAR = 1 << 2;

   /** The kind of values (variables or non-variable expressions), includes VAR.
    */
   public final static int VAL = (1 << 3) | VAR;

   /** The kind of methods.
    */
   public final static int MTH = 1 << 4;

   /** The error kind, which includes all other kinds.
    */
   public final static int ERR = (1 << 5) - 1;

   /** The set of all kinds.
    */
   public final static int AllKinds = ERR;

   /** Kinds for erroneous symbols that complement the above
    */
   public static final int ERRONEOUS = 1 << 6;
   public static final int AMBIGUOUS    = ERRONEOUS+1; // ambiguous reference
   public static final int HIDDEN       = ERRONEOUS+2; // hidden method or field
   public static final int STATICERR    = ERRONEOUS+3; // nonstatic member from static context
   public static final int ABSENT_VAR   = ERRONEOUS+4; // missing variable
   public static final int WRONG_MTHS   = ERRONEOUS+5; // methods with wrong arguments
   public static final int WRONG_MTH    = ERRONEOUS+6; // one method with wrong arguments
   public static final int ABSENT_MTH   = ERRONEOUS+7; // missing method
   public static final int ABSENT_TYP   = ERRONEOUS+8; // missing type
   
 } // End of Flags //
 
 public class TypeTags {

   private TypeTags() {}

   /** The tag of the basic type `byte'.
    */
   public static final int BYTE = 1;

   /** The tag of the basic type `char'.
    */
   public static final int CHAR = BYTE+1;

   /** The tag of the basic type `short'.
    */
   public static final int SHORT = CHAR+1;

   /** The tag of the basic type `int'.
    */
   public static final int INT = SHORT+1;

   /** The tag of the basic type `long'.
    */
   public static final int LONG = INT+1;

   /** The tag of the basic type `float'.
    */
   public static final int FLOAT = LONG+1;

   /** The tag of the basic type `double'.
    */
   public static final int DOUBLE = FLOAT+1;

   /** The tag of the basic type `boolean'.
    */
   public static final int BOOLEAN = DOUBLE+1;

   /** The tag of the type `void'.
    */
   public static final int VOID = BOOLEAN+1;

   /** The tag of all class and interface types.
    */
   public static final int CLASS = VOID+1;

   /** The tag of all array types.
    */
   public static final int ARRAY = CLASS+1;

   /** The tag of all (monomorphic) method types.
    */
   public static final int METHOD = ARRAY+1;

   /** The tag of all package "types".
    */
   public static final int PACKAGE = METHOD+1;

   /** The tag of all (source-level) type variables.
    */
   public static final int TYPEVAR = PACKAGE+1;

   /** The tag of all type arguments.
    */
   public static final int WILDCARD = TYPEVAR+1;

   /** The tag of all polymorphic (method-) types.
    */
   public static final int FORALL = WILDCARD+1;

   /** The tag of the bottom type <null>.
    */
   public static final int BOT = FORALL+1;

   /** The tag of a missing type.
    */
   public static final int NONE = BOT+1;

   /** The tag of the error type.
    */
   public static final int ERROR = NONE+1;

   /** The tag of an unknown type
    */
   public static final int UNKNOWN = ERROR+1;

   /** The tag of all instantiatable type variables.
    */
   public static final int UNDETVAR = UNKNOWN+1;

   /** The number of type tags.
    */
   public static final int TypeTagCount = UNDETVAR+1;

   /** The maximum tag of a basic type.
    */
   public static final int lastBaseTag = BOOLEAN;

   /** The minimum tag of a partial type
    */
   public static final int firstPartialTag = ERROR;
 }
 
 public static class Type {
   
   /** If this switch is turned on, the names of type variables
    *  and anonymous classes are printed with hashcodes appended.
    */
   public static boolean moreInfo = false;

   /** The tag of this type.
    *
    *  @see TypeTags
    */
   public int tag;
   
 }
 
} // End of the class //

/*
 * ##################################################################
 * ##################################################################
 * ##################################################################
 * ##################################################################
 * NOTE HELPER CLASS, ONLY USED FOR JVM BYTECODE CONSTANTS, NOT USED IN ANY MAJOR CAPACITY
 * ##################################################################
 * ##################################################################
 * ##################################################################
 * NOTE HELPER CLASS, ONLY USED FOR JVM BYTECODE CONSTANTS, NOT USED IN ANY MAJOR CAPACITY
 * ##################################################################
 * ##################################################################
 * ##################################################################
 * 
 */