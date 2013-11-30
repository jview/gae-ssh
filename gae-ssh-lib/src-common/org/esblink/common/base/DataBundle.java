package org.esblink.common.base;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
 
 public class DataBundle
   implements Serializable
 {
   private static final long serialVersionUID = 1L;
   private boolean bool;
   private char ch;
   private byte b;
   private short s;
   private int i;
   private long l;
   private float f;
   private double d;
   private Object object;
   private String string;
   private File file;
   private Exception exception;
   private List list = new ArrayList();
 
   private Map map = new HashMap();
   private Date date;
   private Long id;
   private String code;
   private String result;
   private IEntity entity;
 
   public boolean getBoolean()
   {
     return this.bool;
   }
 
   public DataBundle setBoolean(boolean bool) {
     this.bool = bool;
     return this;
   }
 
   public char getChar()
   {
     return this.ch;
   }
 
   public DataBundle setChar(char ch) {
     this.ch = ch;
     return this;
   }
 
   public byte getByte()
   {
     return this.b;
   }
 
   public DataBundle setByte(byte b) {
     this.b = b;
     return this;
   }
 
   public short getShort()
   {
     return this.s;
   }
 
   public DataBundle setShort(short s) {
     this.s = s;
     return this;
   }
 
   public int getInt()
   {
     return this.i;
   }
 
   public DataBundle setInt(int i) {
     this.i = i;
     return this;
   }
 
   public long getLong()
   {
     return this.l;
   }
 
   public DataBundle setLong(long l) {
     this.l = l;
     return this;
   }
 
   public float getFloat()
   {
     return this.f;
   }
 
   public DataBundle setFloat(float f) {
     this.f = f;
     return this;
   }
 
   public double getDouble()
   {
     return this.d;
   }
 
   public DataBundle setDouble(double d) {
     this.d = d;
     return this;
   }
 
   public Object getObject()
   {
     return this.object;
   }
 
   public DataBundle setObject(Object object) {
     this.object = object;
     return this;
   }
 
   public String getString()
   {
     return this.string;
   }
 
   public DataBundle setString(String string) {
     this.string = string;
     return this;
   }
 
   public File getFile()
   {
     return this.file;
   }
 
   public DataBundle setFile(File file) {
     this.file = file;
     return this;
   }
 
   public Exception getException()
   {
     return this.exception;
   }
 
   public DataBundle setException(Exception exception) {
     this.exception = exception;
     return this;
   }
 
   public List getList()
   {
     return this.list;
   }
 
   public DataBundle setList(List list) {
     this.list = list;
     return this;
   }
 
   public Map getMap()
   {
     return this.map;
   }
 
   public DataBundle setMap(Map map) {
     this.map = map;
     return this;
   }
 
   public Date getDate()
   {
     return this.date;
   }
 
   public DataBundle setDate(Date date) {
     this.date = date;
     return this;
   }
 
   public Long getId() {
     return this.id;
   }
 
   public DataBundle setId(Long id) {
     this.id = id;
     return this;
   }
 
   public String getCode() {
     return this.code;
   }
 
   public DataBundle setCode(String code) {
     this.code = code;
     return this;
   }
 
   public String getResult() {
     return this.result;
   }
 
   public DataBundle setResult(String result) {
     this.result = result;
     return this;
   }
 
   public IEntity getEntity() {
     return this.entity;
   }
 
   public DataBundle setEntity(IEntity entity) {
     this.entity = entity;
     return this;
   }
 }

