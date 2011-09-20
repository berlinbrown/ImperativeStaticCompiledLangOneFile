/**
 * Copyright (c) 2006-2010 Berlin Brown. All Rights Reserved
 * 
 * http://www.opensource.org/licenses/bsd-license.php
 * 
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer. * Redistributions in binary
 * form must reproduce the above copyright notice, this list of conditions and
 * the following disclaimer in the documentation and/or other materials provided
 * with the distribution. * Neither the name of the Botnode.com (Berlin Brown)
 * nor the names of its contributors may be used to endorse or promote products
 * derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * 
 * **********************************************
 * File :
 * CompiledLangOneFile.java 
 * 
 * Date: 7/25/2011 
 * 
 * bbrown Contact: Berlin Brown
 * <berlin dot brown at gmail.com>
 * 
 * What is this application used for? : It is a simple proof of concept
 * programming language
 * 
 * OctaneMini, simple programming language in Java with one source file. The
 * entire implementation is contained in this file. A suite of test cases are
 * also provided in a separate class.
 * 
 * The goal of this project is to implement a simple stack based language with
 * as few operations s possible. This is a turing complete proof of concept
 * language.
 * 
 * keywords: java, simple, brainf**k, bf, simple,  recursive descent parsing
 * 
 * URLs:
 * 
 * https://github.com/berlinbrown 
 * http://twitter.com/#!/berlinbrowndev2
 * 
 * **********************************************
 */

/*
 Notes on JVM spec files for compilation:
 See:
 http://java.sun.com/docs/books/vmspec/2nd-edition/html/ClassFile.doc.html 
 -------------------------------------- 
 cp_info {
     u1 tag;
     u1 info[];
 }
 ClassFile {
      u4 magic;
      u2 minor_version;
      u2 major_version;
      u2 constant_pool_count;
      cp_info constant_pool[constant_pool_count-1];
      
      u2 access_flags;
      u2 this_class;
      u2 super_class;
      u2 interfaces_count;
      u2 interfaces[interfaces_count];
      u2 fields_count;
      field_info fields[fields_count];
      u2 methods_count;
      method_info methods[methods_count];
      u2 attributes_count;
      attribute_info attributes[attributes_count];
    }
    
    field_info {
      u2 access_flags;
      u2 name_index;
      u2 descriptor_index;
      u2 attributes_count;
      attribute_info attributes[attributes_count];
    }
    
    method_info {
      u2 access_flags;
      u2 name_index;
      u2 descriptor_index;
      u2 attributes_count;
      attribute_info attributes[attributes_count];
    }
    
    Code_attribute {
      u2 attribute_name_index;
      u4 attribute_length;
      u2 max_stack;
      u2 max_locals;
      u4 code_length;
      u1 code[code_length];
      u2 exception_table_length;
      {     u2 start_pc;
              u2 end_pc;
              u2  handler_pc;
              u2  catch_type;
      } exception_table[exception_table_length];
      u2 attributes_count;
      attribute_info attributes[attributes_count];
    }
 --------------------------------------
 */

package org.berlin.staticlang;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.berlin.staticlang.CompiledLangOneFile.VisitorTreeWalkerNamespace.AssignmentNode;
import org.berlin.staticlang.CompiledLangOneFile.VisitorTreeWalkerNamespace.ExpressionNode;
import org.berlin.staticlang.CompiledLangOneFile.VisitorTreeWalkerNamespace.IProgramVisitor;
import org.berlin.staticlang.CompiledLangOneFile.VisitorTreeWalkerNamespace.NumberNode;
import org.berlin.staticlang.CompiledLangOneFile.VisitorTreeWalkerNamespace.PlusNode;
import org.berlin.staticlang.CompiledLangOneFile.VisitorTreeWalkerNamespace.ProgramRootNode;
import org.berlin.staticlang.CompiledLangOneFile.VisitorTreeWalkerNamespace.ProgramVisitor;
import org.berlin.staticlang.CompiledLangOneFile.VisitorTreeWalkerNamespace.SymbolNode;
import org.berlin.staticlang.JavaCompilerHelper.ByteBuffer;
import org.berlin.staticlang.JavaCompilerHelper.Name;
import org.berlin.staticlang.JavaCompilerHelper.Name.Table;
import org.berlin.staticlang.JavaCompilerHelper.Pool;

/**
 * OctaneMini, simple programming language in Java with one source file.  The entire implementation
 * is contained in this file.
 *     
 * @author berlinberlin (berlin.brown at gmail.com)
 */
public class CompiledLangOneFile {

  /**
   * Check for this file in the current working directory.
   */
  public static final String DEFAULT_CWD_SRC_FILE = "main.octane";
  public static final String DEFAULT_CWD_OUT_FILE = "Main.class";

  public static final String APP = "CompiledLangOneFile";
  public static final String VERSION = "0.0.2";
 
  public static final char EOF = (char) -1;
  public static final String EOF_TOKEN = "EOF";
  
  /**
   * Verbose output, this is controlled by the code stack.
   */
  private boolean verbose = false;

  private String input;
  
  private String outputClassFilename = DEFAULT_CWD_OUT_FILE;
  
  /**
   * Active character used during lexing.
   */
  private char c;

  /**
   * Byte position pointer during lexing. 
   */
  private int p = 0;
  
  /**
   * Representation of all tokens.
   */
  private final List<LangType> code = new LinkedList<LangType>(); 
  
  private int parserIndexTokenP = 0;
  private int parserLookAheadK = 2;
  private int codeConsumePtr = 0;
  
  /**
   * Run interpret instead of compile.
   */
  private boolean interpretMode = true;
  
  /**
   * Lookahead buffer.
   */
  private LangType [] lookahead = new LangType[parserLookAheadK]; 
  
  private MemorySymbolSpace globalMemory = new MemorySymbolSpace(); 
  
  /********************************************************
   * BEGIN Java compiler logic, compile to java bytecode
   * comp142
   ********************************************************/
  
  /**
   * Compile tree into Java bytecode class.
   */
  public class Compiler {
    
    private final CompiledLangOneFile app;
    
    private OutputStream out;    
    
    /**
     * The initial sizes of the data and constant pool buffers. sizes are
     * increased when buffers get full.
     */
    static final int DATA_BUF_SIZE = 0x0fff0;

    static final int POOL_BUF_SIZE = 0x1fff0;
    
    private ByteBuffer databuf = new ByteBuffer(DATA_BUF_SIZE);
    /**
     * An output buffer for the constant pool.
     */
    private ByteBuffer poolbuf = new ByteBuffer(POOL_BUF_SIZE);
    /**
     * The constant pool.
     */
    private Pool pool = new Pool();
    
    private byte[] code = new byte[64];

    /** 
     * The current code pointer.
     */
    public int cp = 0;
    
    public Compiler(final CompiledLangOneFile app) {
      this.app = app;
    }
    
    /** 
     * Emit a byte of code.
     */
    private  void emit1(int od) {        
        if (cp == code.length) {
            byte[] newcode = new byte[cp * 2];
            System.arraycopy(code, 0, newcode, 0, cp);
            code = newcode;
        }        
        code[cp++] = (byte)od;                
        System.out.println("Code : write to buffer : /emit1/ code[cp] " + code[(cp-1)] + " index=" + (cp-1));
    }

    /** Emit two bytes of code.
     */
    private void emit2(int od) {        
        if (cp + 2 > code.length) {
            emit1(od >> 8);
            emit1(od);
        } else {
            code[cp++] = (byte)(od >> 8);
            System.out.println("Code : write to buffer : /emit2/ code[cp] " + code[(cp-1)] + " index=" + (cp-1));
            code[cp++] = (byte)od;
            System.out.println("Code : write to buffer : /emit2/ code[cp] " + code[(cp-1)] + " index=" + (cp-1));
            
        }        
    }

    /** Emit four bytes of code.
     */
    public void emit4(int od) {
        if (cp + 4 > code.length) {
            emit1(od >> 24);
            emit1(od >> 16);
            emit1(od >> 8);
            emit1(od);
        } else {
            code[cp++] = (byte)(od >> 24);
            System.out.println("Code : write to buffer : /emit4/ code[cp] " + code[(cp-1)] + " index=" + (cp-1));
            code[cp++] = (byte)(od >> 16);
            System.out.println("Code : write to buffer : /emit4/ code[cp] " + code[(cp-1)] + " index=" + (cp-1));
            code[cp++] = (byte)(od >> 8);
            System.out.println("Code : write to buffer : /emit4/ code[cp] " + code[(cp-1)] + " index=" + (cp-1));
            code[cp++] = (byte)od;
            System.out.println("Code : write to buffer : /emit4/ code[cp] " + code[(cp-1)] + " index=" + (cp-1));
        }
    }
    
    /** 
     * Emit an opcode.
     */
    private void emitop(int op) {
        System.out.println(">> !!! CALL emit op");                
        emit1(op);        
    }
    /** 
     * Emit newarray.
     */
    public void emitNewarray(int elemcode, Type arrayType) {
        System.out.println("Code : CALL emit new array emit1=" + elemcode);
        emitop(newarray);        
        emit1(elemcode);
    }
    /** 
     * Emit anewarray.
     */
    public void emitAnewarray(int od, Type arrayType) {
        emitop(anewarray);        
        emit2(od);        
    }
    
    /** Emit an invokeinterface instruction.
     */
    public void emitInvokeinterface(int meth, int argsize) {
        System.out.println(">> emit invoke interface meth=" + meth);          
        emitop(invokeinterface);        
        emit2(meth);
        emit1(argsize + 1);
        emit1(0);
        System.out.println(">> end of emit invoke");        
    }
    /** Emit an invokespecial instruction.
     */
    public void emitInvokespecial(int meth, int argsize) {
        System.out.println(">> emit invoke special");        
        emitop(invokespecial);        
        emit2(meth);                       
        System.out.println("End of emit invoke special");
    }
    
    /** Emit an invokestatic instruction.
     */
    public void emitInvokestatic(int meth, int argsize) {
        System.out.println(">> emit invoke static");        
        emitop(invokestatic);        
        emit2(meth);              
        System.out.println(">> emit invoke static meth=" + meth);
    }

    /** 
     * Emit an invokevirtual instruction.
     */
    public void emitInvokevirtual(int meth, int argsize) {
        System.out.println(">> emit invoke virtual");        
        emitop(invokevirtual);        
        emit2(meth);             
        System.out.println(">> end emit invoke virtual" + meth);
    }
    
    /** 
     * Emit an opcode with no operand field.
     */
    public void emitop0(int op) {
        System.out.println(">> emit op 0 (no operand), op=" + op);
        emitop(op);
    }
    
    /** 
     * Emit an opcode with a one-byte operand field.
     */
    public void emitop1(int op, int od) {
        emitop(op);        
        emit1(od);        
    }
    
    /** Emit an opcode with a one-byte operand field;
     * widen if field does not fit in a byte.
     */
   public void emitop1w(int op, int od) {
       System.out.println(">> emit op 1w (one byte oper), op=" + op +  " od=" + od);
       if (od > 0xFF) {
           emitop(wide);
           emitop(op);
           emit2(od);
       } else {
           emitop(op);
           emit1(od);
       }
    }
    
    /** Emit an opcode with two one-byte operand fields;
     *  widen if either field does not fit in a byte.
     */
    public void emitop1w(int op, int od1, int od2) {
        System.out.println(">> emit op 1w (two one byte oper), op=" + op +" od=" + od1 + " od2=" + od2);
        if (od1 > 0xFF || od2 < -128 || od2 > 127) {
            emitop(wide);
            emitop(op);
            emit2(od1);
            emit2(od2);
        } else {
            emitop(op);
            emit1(od1);
            emit1(od2);
        }        
    }    

    /** Emit an opcode with a two-byte operand field.
     */
    public void emitop2(int op, int od) {
        System.out.println(">> emit op2 (two byte), op=" + op + " od=" + od);
        emitop(op);     
        emit2(od);
    }
    
    /** Emit an opcode with a four-byte operand field.
     */
    public void emitop4(int op, int od) {
        System.out.println(">> emit op4 four byte op=" + op + " od=" + od );
        emitop(op);        
        emit4(od);        
    }
    
    /**
     * Write the tree data to a Java bytecode class file.
     * @param programTree
     */
    public void writeClassFile(final AbstractSyntaxTree programTree) {
      
      databuf.reset();
      poolbuf.reset();
      try {
        
        /////////////////////////////////////////
        // Write databuf
        // The databuf is composed of mostly the Code block
        /////////////////////////////////////////
        final int flags = 33;
        System.out.println(">> Writing data buffer to databuf");
        databuf.appendChar(flags);
        
        // reference to class type
        databuf.appendChar(2);
        // supertype.tag == CLASS ? pool.put(supertype.tsym)
        databuf.appendChar(3);
        // write zero interfaces
        databuf.appendChar(0);
        
        // Fields count
        databuf.appendChar(0);
        
        // Writing methods
        databuf.appendChar(1);
        databuf.appendChar(1073741825);
        
        // Field Name (name and type, #4, 
        databuf.appendChar(4);
        databuf.appendChar(5);
               
        // write zero for begin attributes, later for replace //
        // pos 18-20
        System.out.println();
        System.out.println("External Type - writing");
        databuf.appendChar(1);
        
        // Now at the code block
        databuf.appendChar(6);
        // Code size, replace 17/int
        databuf.appendInt(17);
        
        //////////////////////////////////////////
        // Write code block
        // Max stack 1, max locals, for class
        databuf.appendChar(1);
        databuf.appendChar(1);
        
        // Write code pointer length and                
        // Write code data
        emit1(42);
        emit1(-73);        
        emit2(1);
        emit1(-79);               
        
        databuf.appendInt(cp);
        databuf.appendBytes(code, 0, cp);
        // End of code block
        
        // Then catch info length, 0 0
        databuf.appendChar(0);
        databuf.appendChar(0);
        
        // ??
        databuf.appendChar(0);        
        
        /////////////////////////////////////////
        System.out.println();
        System.out.println(">> Writing poolbuf, constant pool");
        poolbuf.appendInt(JavaCompilerHelper.ClassFile.JAVA_MAGIC); 
        poolbuf.appendChar(00);
        poolbuf.appendChar(49);
        
        // Write the size of constant pool
        poolbuf.appendChar(10);
               
/*
 Example program, constant pool:
 -------------------------------
Constant pool:
const #1 = Method       #3.#7;  //  java/lang/Object."<init>":()V
const #2 = class        #8;     //  Test2
const #3 = class        #9;     //  java/lang/Object
const #4 = Asciz        <init>;
const #5 = Asciz        ()V;
const #6 = Asciz        Code;
const #7 = NameAndType  #4:#5;//  "<init>":()V
const #8 = Asciz        Test2;
const #9 = Asciz        java/lang/Object;
 -------------------------------

public Test2();
  Code:
   Stack=1, Locals=1, Args_size=1
   0:   aload_0
   1:   invokespecial   #1; //Method java/lang/Object."<init>":()V
   4:   return       
 */
        
        // Write 3 is index in pool, 7 is name type
        // Method, #3.#7;  //  java/lang/Object."<init>":()V
        // Constructor. (1)        
        poolbuf.appendByte(JavaCompilerHelper.ClassFile.CONSTANT_Methodref);
        poolbuf.appendChar(3);
        poolbuf.appendChar(7);
        
        // For class symbol (2) //        
        poolbuf.appendByte(JavaCompilerHelper.ClassFile.CONSTANT_Class);
        poolbuf.appendChar(8);
        
        // java/lang/Object (3)
        poolbuf.appendByte(JavaCompilerHelper.ClassFile.CONSTANT_Class);
        poolbuf.appendChar(9);
        
        // 4 is first ascii (4)
        // const #4 = Asciz  <init>;         
        //-------------------
        final Table table = Table.make();
        poolbuf.appendByte(JavaCompilerHelper.ClassFile.CONSTANT_Utf8);
        byte[] bs = table.init.toUtf();
        poolbuf.appendChar(bs.length);
        poolbuf.appendBytes(bs, 0, bs.length);                
        //-------------------
        
        // Void? (5)
        final Name v = table.fromString("()V");        
        bs = v.toUtf();
        poolbuf.appendByte(JavaCompilerHelper.ClassFile.CONSTANT_Utf8);        
        poolbuf.appendChar(bs.length);
        poolbuf.appendBytes(bs, 0, bs.length);
             
        // (6)
        poolbuf.appendByte(JavaCompilerHelper.ClassFile.CONSTANT_Utf8);
        bs = table.Code.toUtf();
        poolbuf.appendChar(bs.length);
        poolbuf.appendBytes(bs, 0, bs.length);
        
        // 7 / #4:#5;//  "<init>":()V
        poolbuf.appendByte(JavaCompilerHelper.ClassFile.CONSTANT_NameandType);
        poolbuf.appendChar(4);
        poolbuf.appendChar(5);
        
        // 8
        final Name test2 = table.fromString("Main");        
        bs = test2.toUtf();
        poolbuf.appendByte(JavaCompilerHelper.ClassFile.CONSTANT_Utf8);        
        poolbuf.appendChar(bs.length);
        poolbuf.appendBytes(bs, 0, bs.length);
        
        // 9 / java/lang/Object
        final Name nmobj = table.fromString("java/lang/Object");
        bs = nmobj.toUtf();
        poolbuf.appendByte(JavaCompilerHelper.ClassFile.CONSTANT_Utf8);        
        poolbuf.appendChar(bs.length);
        poolbuf.appendBytes(bs, 0, bs.length);
        System.out.println(">> End of write constant pool");
        System.out.println();
                        
        // Write all elements //
        poolbuf.appendBytes(databuf.elems, 0, databuf.length); 
        out.write(poolbuf.elems, 0, poolbuf.length);
      } catch (final Exception e) {
        throw new LanguageError(e.getMessage());
      } // End of the try - catch //
      
    } // End of method //
    
    public void writeClassFileConstantPool(final AbstractSyntaxTree programTree) {
      poolbuf.appendChar(0);
    }
    
    public void openWriter() throws FileNotFoundException {
      this.out = new FileOutputStream(app.outputClassFilename);
      System.out.println("Opened output class file");
    }
    
    public void closeWriter() {
       if (this.out != null) {
         try {
          this.out.close();
          System.out.println("Closed output class file");
        } catch (IOException e) {
          e.printStackTrace();
        }
       }
    }    
    /**
     * Write a character into given byte buffer; byte buffer will not be grown.
     */
    void putChar(ByteBuffer buf, int op, int x) {
        buf.elems[op] = (byte) ((x >> 8) & 0xFF);
        buf.elems[op + 1] = (byte) ((x) & 0xFF);
    }

    /**
     * Write an integer into given byte buffer; byte buffer will not be grown.
     */
    void putInt(ByteBuffer buf, int adr, int x) {
        buf.elems[adr] = (byte) ((x >> 24) & 0xFF);
        buf.elems[adr + 1] = (byte) ((x >> 16) & 0xFF);
        buf.elems[adr + 2] = (byte) ((x >> 8) & 0xFF);
        buf.elems[adr + 3] = (byte) ((x) & 0xFF);
    }
    
  } // End of compiler class //
  
  /********************************************************
   * BEGIN Visitor Pattern Node logic.  'Continue on' dispatch.
   * vis121
   ********************************************************/
    
  /**
   * Store symbols in global memory     
   */
  public class MemorySymbolSpace {
    private Map<String, Object> symbols = new HashMap<String, Object>();
    public void put(final String id, final Object data) {
      symbols.put(id, data);
    }
  };
  
  public interface VisitorTreeWalkerNamespace {    
    /**
     * Visitor contains visit methods for each node.
     */
    public interface IProgramVisitor {
      Object visit(final SymbolNode nodeOnVisit);
      Object visit(final ProgramRootNode nodeOnVisit);
      Object visit(final AssignmentNode nodeOnVisit);
      Object visit(final PlusNode nodeOnVisit);
      Object visit(final NumberNode nodeOnVisit);
    };
    
    /**
     * Visitor Implementation.
     */
    public class ProgramVisitor implements IProgramVisitor {
      
      private final MemorySymbolSpace globalMemory;
      private final CompiledLangOneFile app;
      private final Compiler compiler;
      
      public ProgramVisitor(final MemorySymbolSpace globalMemory, final CompiledLangOneFile app, final Compiler compiler) {
        this.globalMemory = globalMemory;
        this.app = app;
        this.compiler = compiler;
      }
      /**
       * With visit, continue to next node type.
       */
      @Override
      public Object visit(final SymbolNode nodeOnVisit) {
        // Return token with symbol data
        return nodeOnVisit.data();                
      }

      /** 
       * Visitor dispatch based on node token type 
       */
      @Override
      public Object visit(final ProgramRootNode nodeOnVisit) {
        compiler.writeClassFile(nodeOnVisit);
        if (nodeOnVisit.children() == null || nodeOnVisit.children().size() == 0) {
          return new Object();
        }
        for (final AbstractSyntaxTree node : nodeOnVisit.children()) {          
            if (node instanceof AssignmentNode) {
              node.visit(this);            
          } // End of if sub nodes //
        } // End of the for //
        return new Object();
      } // End of program dispatch //
      
      @Override
      public Object visit(final AssignmentNode nodeOnVisit) { 
        // Visit, continue to symbol node
        final Object symbol = nodeOnVisit.left().visit(this);
        final Object valOfAssignment = nodeOnVisit.right().visit(this);        
        this.globalMemory.put(String.valueOf(symbol), valOfAssignment);
        if (app.interpretMode && String.valueOf(symbol).equalsIgnoreCase("print")) {
          // Print all data in memory //
          System.out.println(">> Printing Memory Objects");
          for (final String key : this.globalMemory.symbols.keySet()) {
            System.out.println(key + " -> " + this.globalMemory.symbols.get(key));
          }
        } // End of the if //
        return new Object();
      }  
      @Override
      public Object visit(final PlusNode nodeOnVisit) { 
        final Object lhsNumVal = nodeOnVisit.left().data().obj();
        Object rhsNumVal = 0.0d;
        if (nodeOnVisit.right() instanceof PlusNode) {          
          rhsNumVal = nodeOnVisit.right().visit(this);
        } else {
          rhsNumVal = nodeOnVisit.right().data().obj();
        }
        return (Double) lhsNumVal + (Double) rhsNumVal;
      }
      @Override
      public Object visit(final NumberNode nodeOnVisit) {
        // No continue on, just a number
        return nodeOnVisit.data().obj();
      }   
    } // End of the class ///
    
    /**
     * AST Expression Node
     */
    public interface ExpressionNode {                     
      Object visit(final IProgramVisitor visitor);      
    } // End of the class //    
    
    public class SymbolNode extends AbstractSyntaxTree {
      public SymbolNode(final LangType tokenAtExpression) {
        super(tokenAtExpression);        
      }
      @Override
      public Object visit(final IProgramVisitor visitor) {
        return visitor.visit(this);
      }      
    }; 
    public class NumberNode extends AbstractSyntaxTree {
      public NumberNode(final LangType tokenAtExpression) {
        super(tokenAtExpression);        
      }
      @Override
      public Object visit(final IProgramVisitor visitor) {
        return visitor.visit(this);
      }      
    }; 
    
    public class AssignmentNode extends AbstractSyntaxTree {
      public AssignmentNode(final LangType tokenAtExpression) {
        super(tokenAtExpression);        
      }
      @Override
      public Object visit(final IProgramVisitor visitor) {
        return visitor.visit(this);
      }      
    };
    
    public class PlusNode extends AbstractSyntaxTree {
      public PlusNode(final LangType tokenAtExpression) {
        super(tokenAtExpression);        
      }
      @Override
      public Object visit(final IProgramVisitor visitor) {
        return visitor.visit(this);
      }      
    };    
    public class ProgramRootNode extends AbstractSyntaxTree {
      public ProgramRootNode() {
        super();        
      }  
      public ProgramRootNode(final LangType tokenAtExpression) {
        super(tokenAtExpression);        
      }  
      @Override
      public Object visit(final IProgramVisitor visitor) {
        return visitor.visit(this);
      }      
    };     
  } // End of the visitor name space  //  
  
  /********************************************************
   * BEGIN Intermediate Parser Logic
   * par124
   ********************************************************/
  
  /**
   * Consume tokens and respond to the tokens, interpret.
   * Parse the input tokens
   */
  public AbstractSyntaxTree parser() {
    final AbstractSyntaxTree abstractSyntaxTree = new ProgramRootNode();      
    this.consume();
    do {
      final LangType token = this.nextTokenLexing();
      code.add(token);
    } while (this.c != EOF);    
    
    if (!code.isEmpty()) {
      System.out.println("=>>" + code + "<<=");
      codeConsumePtr = 0;
      lookahead = new LangType[parserLookAheadK];
      for (int i = 1; i <= this.parserLookAheadK; i++) {
        // Prime the lookahead buffer
        this.parserConsume();
      } // End of for, consume tokens, add to lookahead
      
      this.parserRuleProgram(abstractSyntaxTree);
    } // End of if - check code queue //    
    
    // Done with lexing and parser, return abstract syntax tree for later analysis
    this.printAbstractSyntaxTree(abstractSyntaxTree);
    return abstractSyntaxTree;
  }
  
  public void parserConsume() {
    // Get next token //
    if (codeConsumePtr < code.size()) {
      lookahead[parserIndexTokenP] = code.get(codeConsumePtr);
    } else {
      lookahead[parserIndexTokenP] = code.get(code.size()-1);
      return;
    } // End of the if - else - get next token //           
    parserIndexTokenP = (parserIndexTokenP + 1) % parserLookAheadK;    
    codeConsumePtr++;      
  }     
  public LangType parserLookAhead(final int i) {
    final int fetchindex = (parserIndexTokenP + i - 1) % this.parserLookAheadK;
    return lookahead[fetchindex];
  }
  public void parserMatch(final LangType matchToken) {
    final LangType lookAhead1 = this.parserLookAhead(1);
    if (matchToken == null) {  
      throw new LanguageError("Language Error : invalid match token");
    } else {
      if (this.parserLookAhead(1) != null && this.parserLookAhead(1).eql(matchToken)) {
        this.parserConsume();
      } else {
        throw new LanguageError("Language Error : expecting='" + matchToken + "' but found='" + lookAhead1 + "'");
      } // End of the if - else //
    } // End of first check //
  }
  
  public void parserMatchType(final LangType matchToken) {
    final LangType lookAhead1 = this.parserLookAhead(1);
    if (matchToken == null) {  
      throw new LanguageError("Language Error : invalid match token");
    } else {
      if (this.parserLookAhead(1) != null && this.parserLookAhead(1).eqlt(matchToken)) {        
        this.parserConsume();
      } else {
        throw new LanguageError("Language Error : expecting=" + matchToken + " ; found=" + lookAhead1);
      } // End of the if - else //
    } // End of first check //
  }    
  
  /**
   * Parse according to the grammar statement.
   * program: (statement)+ EOF
   */
  public void parserRuleProgram(final AbstractSyntaxTree ast) {        
    boolean checkRuleEndOfFile = this.parserLookAhead(1) != null
    && this.parserLookAhead(1).eql(new LangTypeToken("EOF"));    
    while (!checkRuleEndOfFile) {      
      this.parserRuleStatements(ast);     
      //this.parserConsume();
      checkRuleEndOfFile = this.parserLookAhead(1) != null
      && this.parserLookAhead(1).eql(new LangTypeToken("EOF"));      
      break;
    } // End of the while //
  }
  
  /**
   * Parse according to the grammar statement.
   * statement: ID '=' expression ;
   * 
   * match(ID); match('='); expr()
   */
  public void parserRuleStatements(final AbstractSyntaxTree ast) {    
    boolean checkRuleSemicolonDelim = this.parserLookAhead(1) != null
    && this.parserLookAhead(1).eql(new LangTypeToken(";"));    
    do {
      this.parserRuleAssignmentStatement(ast);      
      checkRuleSemicolonDelim = this.parserLookAhead(1) != null
      && this.parserLookAhead(1).eql(new LangTypeToken(";"));
      final boolean checkRuleEndOfFile = this.parserLookAhead(1) != null
      && this.parserLookAhead(1).eql(new LangTypeToken("EOF"));      
      if (checkRuleEndOfFile) {
        break;
      } else {
        // If not end of file, parse match 
        this.parserMatch(new LangTypeToken(";"));
      } // End of if rule check, EOF
    } while (checkRuleSemicolonDelim);                     
  }
  
  /**
   * Parse according to the grammar statement.
   * statement: ID '=' expression ;
   * 
   * match(ID); match('='); expr()
   */
  public void parserRuleAssignmentStatement(final AbstractSyntaxTree ast) {    
    final LangType p1 = this.parserLookAhead(1);
    final LangType p2 = this.parserLookAhead(2);
    boolean checkRuleSymbol = p1 != null && p1.eqlt(new LangTypeTokenSymbol("<none>"));
    boolean checkRuleAssignment = p2 != null && p2.eql(new LangTypeToken("="));       
        
    if (checkRuleSymbol && checkRuleAssignment) {    
      this.parserMatchType(new LangTypeTokenSymbol("<none>"));           
      this.parserMatch(new LangTypeToken("="));      
                  
      AbstractSyntaxTree continueWithAssignTree = null;      
      final AbstractSyntaxTree assignTree = new AssignmentNode(p2);
      assignTree.addChildToAbstractSyntaxTree(new SymbolNode(p1));        
      ast.addChildToAbstractSyntaxTree(assignTree);
      continueWithAssignTree = assignTree;      
      
      // Continue with rule for numbers           
      this.parserRuleNumberExpression(continueWithAssignTree);                             
                                        
    } else if (checkRuleSymbol) {
      this.parserMatchType(new LangTypeTokenSymbol("<none>"));
      
    } else {
      System.out.println("Parser rule fail - " + checkRuleSymbol + " // " + checkRuleAssignment + " $ " + p1 + "/" + p2);
    } // End of if - check for assignment rule   
  }
  
  /**
   * Parse according to the grammar statement.
   * expression: NUMBER {expressionRest}
   */
  public void parserRuleNumberExpression(final AbstractSyntaxTree ast) {
    final LangType numberValLhs = this.parserLookAhead(1);    
    
    // Match on a NUMBER rule    
    this.parserMatchType(new LangTypeTokenDouble(-1));
        
    // Continue with match on math operator or empty
    this.parserRuleNumberExpressionRest(ast, numberValLhs);
  }
  
  /**
   * Parse according to the grammar statement.
   * expressionRest: +/- NUMBER {expressionRest}
   */
  public void parserRuleNumberExpressionRest(final AbstractSyntaxTree ast,  final LangType numberValLhs) {
    final LangType p1 = this.parserLookAhead(1);
    final boolean checkRulePlus = p1 != null && p1.eql(new LangTypeToken("+"));    
    if (checkRulePlus) {  
      final LangType plus = new LangTypeToken("+");
      final AbstractSyntaxTree newNodePlus = new PlusNode(plus);
      final AbstractSyntaxTree valnode = new NumberNode(numberValLhs);
      newNodePlus.addChildToAbstractSyntaxTree(valnode);
      
      this.parserMatch(plus);
      final LangType numberValRhs = this.parserLookAhead(1);             
      this.parserMatchType(new LangTypeTokenDouble(-1));
      this.parserRuleNumberExpressionRest(newNodePlus, numberValRhs);      
      ast.addChildToAbstractSyntaxTree(newNodePlus);
    } else {
      final AbstractSyntaxTree valnode = new NumberNode(numberValLhs);
      ast.addChildToAbstractSyntaxTree(valnode);
    } // End of the if - check rule for plus
  }
  
  /********************************************************
   * <BEGIN LEXING/SCANNING LOGIC>
   * Scan for ascii characters and convert to tokens
   * (sect300)
   ********************************************************/
  
  /**
   * Scan for next token.  The input content for the lexer are the ASCII characters.
   * Output basic tokens.
   * 
   * Used with lexing on the input code string data.
   * 
   * @return Token
   */
  public LangType nextTokenLexing() {
    while (c() != EOF) {
      switch (c()) {
      case ' ':
      case '\t':
      case '\n':
      case '\r':
        consumeWhitespace();
        continue;      
      case '#':
        consumeComment();
        continue;
      case '=':
        consume();
        return new LangTypeToken("=");
      case ';':
        consume();
        return new LangTypeToken(";");
      case '(':
        consume();
        return new LangTypeToken("(");
      case ')':
        consume();
        return new LangTypeToken(")");                 
      case '+':
        consume();
        return new LangTypeToken("+");
      case '-':
        consume();
        return new LangTypeToken("-");
      case '*':
        consume();
        return new LangTypeToken("*");
      case '/':
        consume();
        return new LangTypeToken("/");
      case '\0':
        return new LangTypeToken(EOF_TOKEN);
      default:
        if (this.isDigit()) {
          return scanInteger();
        } else if (this.isLetter()) {
          return scanWord();
        }
        final int code = (int) c();
        throw new Error("invalid character: {" + c() + "} code:" + code);
      } // End of Switch //

    } // End of While //
    return new LangTypeToken(EOF_TOKEN);
  }
     
  /**
   * Scan for a token name/integer. Used with lexing on the input code string
   * data.
   * 
   * @return Token
   */
  public LangTypeTokenSymbol scanWord() {
    final StringBuilder buf = new StringBuilder();
    do {
      buf.append(c());
      consume();
    } while (isLetter());
    return new LangTypeTokenSymbol(buf.toString());
  }

  /**
   * Check if character is a letter. Used with lexing on the input code string
   * data.
   * 
   * @return boolean
   */
  public boolean isLetter() {
    return (c() >= 'a') && (c() <= 'z') || (c() >= 'A') && (c() <= 'Z');
  }

  /**
   * Check if character is a digit. Used with lexing on the input code string
   * data.
   * 
   * @return boolean
   */
  public boolean isDigit() {
    return (c() >= '0') && (c() <= '9');
  }

  /**
   * Consume and detect whitespace. Used with lexing on the input code string
   * data.
   */
  public void consumeWhitespace() {
    while (c() == ' ' || c() == '\t' || c() == '\n' || c() == '\r') {
      consume();
    }
  }

  /**
   * Iterate and consume until a newline is encountered
   */
  public void consumeComment() {
    do {
      consume();
    } while (c != '\n' && c != '\r');
  }

  /**
   * Consume character. Used with lexing on the input code string data.
   */
  public void consume() {
    if (p >= input.length()) {
      c = EOF;
    } else {
      c = input.charAt(p);
    } // End of the if - else //
    p++;
  }

  /**
   * Return the current character for lexing.
   * 
   * @return
   */
  public char c() {
    return c;
  }

  /**
   * Set the active character for lexing.
   * 
   * @param c
   */
  public void setc(final char c) {
    this.c = c;
  }

  /**
   * @return the input
   */
  public String getInput() {
    return input;
  }

  /**
   * @param input
   *          the input to set
   */
  public void setInput(String input) {
    this.input = input;
  }
  
  /********************************************************
   * <END OF LEXING/SCANNING LOGIC>
   *********************************************************/
  
  /**
   * Simple type.
   */
  public static interface LangType {
    /**
     * Comparison based on type of token and token data.
     * @param obj
     * @return
     */
    public boolean eql(final LangType obj);
    /**
     * Comparison based on type of token.
     * @param obj
     * @return
     */
    public boolean eqlt(final LangType obj);
    public String token();
    public Object obj();
  }
  
  /**
   * Basic string type.     
   */
  public static class LangTypeToken implements LangType {
      private final String data;
      private int lastEqlState = -1;
      public LangTypeToken(final String d) {
          if (d == null) {
            throw new LanguageError("Invalid input when creating token type");
          }
          this.data = d;
      }
      public String toString() {
          return data;
      }
      public Object obj() {
        return data;
      }
      /**
       * Token represented as a string.
       * @return
       */
      public String token() {
        return data;
      }      
      public boolean eql(final LangType obj) {
        if (obj == null) {
          lastEqlState=-2;
          return false;
        }
        if (this == obj) {
          lastEqlState=1;
          return true;
        }
        // Check if obj is a super class
        if (!this.getClass().isAssignableFrom(obj.getClass())) {
          lastEqlState=-3;
          return false;
        }
        
        if (this.token().equals(obj.token())) {
          lastEqlState=2;
          return true;
        }
        lastEqlState=-9;
        return false;
      }
      public boolean eqlt(final LangType obj) {
        if (obj == null) {
          lastEqlState=-5;
          return false;
        }
        if (this == obj) {
          lastEqlState=5;
          return true;
        }
        // Check if obj is a super class
        if (this.getClass().isAssignableFrom(obj.getClass())) {
          lastEqlState=-7;
          return true;
        }                
        lastEqlState=-99;
        return false;
      }
  } // End of class /
  
  public static class LangTypeTokenDouble extends LangTypeToken {
    private final double data;
    public LangTypeTokenDouble(final double d) {
        super(String.valueOf(d));
        this.data = d;
    }
    public String toString() {
        return String.valueOf(data);
    }
    public Object obj() {
      return data;
    }
    /**
     * Token represented as a string.
     * @return
     */
    public double data() {
      return data;
    }
  } // End of class //
  
  public static class LangTypeTokenSymbol extends LangTypeToken {
    public LangTypeTokenSymbol(final String d) {
      super(d);
    }
  } // End of class //
  
  /**
   * Scan for a token name/integer. Used with lexing on the input code string
   * data.
   * 
   * @return Token
   */
  public LangTypeTokenDouble scanInteger() {
    final StringBuilder buf = new StringBuilder();
    do {
      buf.append(c());
      consume();
    } while (isDigit());
    final double data = Double.parseDouble(buf.toString());
    return new LangTypeTokenDouble(data);
  }  
  
  public static abstract class AbstractSyntaxTree implements ExpressionNode {    
    private LangType expressionData = null;    
    private List<AbstractSyntaxTree> childrenNodes = new ArrayList<AbstractSyntaxTree>();
    public AbstractSyntaxTree() {
      super();
    }
    public AbstractSyntaxTree(final LangType tokenAtExpression) {
      this.expressionData = tokenAtExpression;
    }       
    public LangType data() {
      return this.expressionData;
    }
    public AbstractSyntaxTree left() {
      return childrenNodes.get(0);
    }
    public AbstractSyntaxTree right() {
      return childrenNodes.get(1);
    }
    public List<AbstractSyntaxTree> children() {
      return this.childrenNodes;
    }
    /**
     * Add a full tree node.
     * @param ast
     * @return
     */
    public AbstractSyntaxTree addChildToAbstractSyntaxTree(final AbstractSyntaxTree ast) {      
      this.childrenNodes.add(ast);
      return ast;
    }  
    public String toString() {
      final String nodes = childrenNodes == null || childrenNodes.size() == 0 ? "<NoSubNodes>" : "hasNodes"; 
      return "[AST : data='" + (expressionData == null ? "<null>" : String.valueOf(this.expressionData)) + "'/" + nodes + "]]";
    }
  } // End of the class //
 
  /**
   * Generic language error class implemented as a <code>RuntimeException</code> type.
   */
  public static class LanguageError extends RuntimeException {
    private static final long serialVersionUID = 1L;    
    public LanguageError(final String msg) {
      super(msg);
    }    
  } // End of the class //
  
  public void printAbstractSyntaxTree(final AbstractSyntaxTree ast) {
    if (ast == null) {
      throw new LanguageError("Invalid AST object");
    }
    if (ast.expressionData == null) {
      System.out.println("<Abstract Syntax Tree Data is empty, no data>");
    }
    if (ast.childrenNodes == null || ast.childrenNodes.size() == 0) {
      System.out.println("<Abstract Syntax Tree contains zero nodes>");
    }           
    System.out.println(">> Printing Intermediate Abstract Syntax Tree <<");
    this.printAbstractSyntaxTree(ast, 0);
    System.out.println(">> END Abstract Syntax Tree <<");
  }  
  public void printAbstractSyntaxTree(final AbstractSyntaxTree currentNode, final int level) {
    
    final String levelSpacePadding = String.format("%" + ((level+1)*6) + "s", "").replaceAll(" ", ".");
    if (currentNode == null) {
      System.out.println(level + ": <EndNode>");
      return;
    } else {
      System.out.println(levelSpacePadding + "|");
      System.out.println(levelSpacePadding + "+-->{lvl_"+(level+1)+"}[ Data at Current Node : '" + currentNode + "'");
      int i = 0;     
      for (final AbstractSyntaxTree tree : currentNode.childrenNodes) {
        final String rightOrLeft = i == 0 ? "<left>" : "<right>";
        System.out.println(levelSpacePadding + "  [ Node :(" + rightOrLeft + ")");
        printAbstractSyntaxTree(tree, level+1);
        i++;
      } // End of the for //                 
    } // End of the if - else //
  }    
  /**
   * Write a source file with example code.
   * 
   * @param f
   */
  protected void writeExampleSourceFile(final File f) {
    final String src = new StringBuffer()
    .append("############\n")
    .append("# OctaneMini Example Source\n")
    .append("############\n")
    .toString();    
    FileOutputStream stream = null;
    try {
      stream = new FileOutputStream(f);
      final BufferedOutputStream buf = new BufferedOutputStream(stream);
      final PrintWriter writer = new PrintWriter(buf);
      writer.println(src);
      writer.flush();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (stream != null) {
        try {
          stream.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    } // End of finally //
  } // End of method //

  /********************************************************
   * Java FILE I/O Reading File Content
   * jav123
   ********************************************************/
  
  /**
   * Run interpret with buffered reader.
   * 
   * @param reader
   */
  public void compile(final BufferedReader reader) {
    try {
      String data = "";
      final StringBuffer buf = new StringBuffer();
      do {
        data = reader.readLine();
        if (data != null) {
          buf.append(data).append('\n');
        }
      } while (data != null);
      this.compile(buf.toString());
    } catch (Exception e) {
      e.printStackTrace();
      throw new IllegalStateException("Invalid input source data at run interpreter");
    } // End of the try - catch //
  }

  /**
   * Run against default source file or read the application arguments.
   * 
   * @param args
   */
  public void compile(final String[] args) {
    File f = null;
    FileInputStream stream = null;
    if (args.length == 0) {
      System.out.println("Running without arguments, no input filename found - " + VERSION);
      final File f1 = new File(DEFAULT_CWD_SRC_FILE);
      if (!f1.exists()) {
        // Write a basic source file
        System.out.println("Writing example source file - " + f1);
        this.writeExampleSourceFile(f1);
      }
      f = f1;
    } else if (args.length == 1) {
      final String filename = args[0];
      f = new File(filename);
    } else {
      throw new IllegalStateException("Invalid input file parameters, could not load source file");
    } // End if - args length zero //    
    try {
      stream = new FileInputStream(f);
      final BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
      this.compile(reader);
    } catch (Exception e) {
      e.printStackTrace();
      throw new LanguageError("Invalid read source file : " + e.getMessage());
    } finally {
      if (stream != null) {
        try {
          stream.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    } // End of try - catch finally //
  }

  /**
   * Run with source as a string object.
   * 
   * @param codeSource
   */
  public void compile(final String codeSource) {
    this.input = codeSource;
    final Compiler compiler = new Compiler(this);
    try {
      if (this.verbose) {
        System.out.println("Running : " + APP + "-" + VERSION);
      }
      final AbstractSyntaxTree programAst = this.parser();            
      compiler.openWriter();
      final IProgramVisitor programVisitor = new ProgramVisitor(globalMemory, this, compiler);
      programVisitor.visit((ProgramRootNode)programAst);      
    } catch (Exception e) {
      System.out.println(">>>> Error during interpret <<<<");
      e.printStackTrace();
    } finally {
      compiler.closeWriter();
    } // End of the try - catch //
    
  }

  /********************************************************
   * END Java FILE I/O Reading File Content
   ********************************************************/
  
  /**
   * Main entry and starting point for the application.
   * 
   * @param args
   */
  public static void main(final String[] args) {
    new CompiledLangOneFile().compile(args);
  } // End of Method //

} // End of the Class //
