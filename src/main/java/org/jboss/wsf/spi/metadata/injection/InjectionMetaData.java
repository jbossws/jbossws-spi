/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2009, Red Hat Middleware LLC, and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jboss.wsf.spi.metadata.injection;

/**
 * An injection target specifies a class and a name within
 * that class into which a resource should be injected.
 * 
 * @author ropalka@redhat.com
 */
public final class InjectionMetaData
{
   
   /**
    * Target class to do injection on.
    */
   private final String targetClass;
   /**
    * Target name to do injection on (field or setter method).
    */
   private final String targetName; 
   /**
    * Value class to be injected.
    */
   private final Class<?> valueClass;
   /**
    * JNDI environment entry name to be injected.
    */
   private final String envEntryName;
   /**
    * Specifies environment entry whether value is specified (affects injection behavior).
    */
   private final boolean isValueSpecified;
   /**
    * Cached InjectionMetaData#toString() value.
    */
   private String toString;
   
   /**
    * Constructor.
    * 
    * @param targetClass target class to do injection on
    * @param targetName target name to do injection on (field or method)
    * @param valueClass value class to be injected (can be null)
    * @param envEntryName JNDI environment entry name to be injected
    * @param isValueSpecified specifies whether value is specified (affects injection behavior)
    */
   public InjectionMetaData(String targetClass, String targetName, String valueClass, String envEntryName, boolean isValueSpecified)
   {
      super();
      
      if (targetClass == null)
         throw new IllegalArgumentException("targetClass not specified");
      if (targetName == null)
         throw new IllegalArgumentException("targetName not specified");
      if (envEntryName == null)
         throw new IllegalArgumentException("envEntryName not specified");
      
      this.targetClass = targetClass;
      this.targetName = targetName;
      this.envEntryName = envEntryName;
      this.valueClass = this.valueOf(valueClass);
      this.isValueSpecified = isValueSpecified;
   }

   /**
    * Converts string to supported class type.
    * 
    * @param s to convert to class
    * @return class type or null
    * @throws IllegalArgumentException if wrong value is passed
    */
   private Class<?> valueOf(final String s)
   {
      if (s == null || s.trim().equals("")) 
         return null;
      if (Boolean.class.getName().equals(s)) 
         return Boolean.class; 
      if (Byte.class.getName().equals(s))
         return Byte.class;
      if (Character.class.getName().equals(s)) 
         return Character.class;   
      if (String.class.getName().equals(s)) 
         return String.class;   
      if (Short.class.getName().equals(s)) 
         return Short.class;   
      if (Integer.class.getName().equals(s)) 
         return Integer.class;   
      if (Long.class.getName().equals(s)) 
         return Long.class;   
      if (Float.class.getName().equals(s)) 
         return Float.class;   
      if (Double.class.getName().equals(s)) 
         return Double.class;   

      throw new IllegalArgumentException("Unsupported env entry type: " + s);
   }
   
   /**
    * The injection target class specifies the fully qualified
    * class name that is the target of the injection. The
    * Java EE specifications describe which classes can be an
    * injection target.
    * 
    * @return injection target class
    */
   public String getTargetClass()
   {
      return this.targetClass;
   }
   
   /**
    * The injection target name specifies the target within
    * the specified class.  The target is first looked for as a
    * JavaBeans property name.  If not found, the target is
    * looked for as a field name.
    * 
    * The specified resource will be injected into the target
    * during initialization of the class by either calling the
    * set method for the target property or by setting a value
    * into the named field.
    *
    * @return injection target name
    */
   public String getTargetName()
   {
      return this.targetName;
   }

   /**
    * The env-entry-type element contains the Java language
    * type of the environment entry.  If an injection target
    * is specified for the environment entry, the type may
    * be omitted, or must match the type of the injection
    * target.  If no injection target is specified, the type
    * is required.
    * 
    * @return injection value type
    */
   public Class<?> getValueClass()
   {
      return this.valueClass;
   }
   
   /**
    * The <b>env-entry-name</b> element contains the name of a
    * Deployment Component's environment entry.  The name is a
    * JNDI name relative to the <b>java:comp/env</b> context.
    * The name must be unique within a Deployment Component.
    * 
    * @return JNDI environment entry name to be injected
    */
   public String getEnvEntryName()
   {
      return this.envEntryName;
   }
   
   /**
    * If a value is not specified and injection is requested,
    * no injection will occur and no entry of the specified name
    * will be created.  This allows an initial value to be
    * specified in the source code without being incorrectly
    * changed when no override has been specified.
    * 
    * @return whether value is specified
    */
   public boolean isEnvEntryValueSpecified()
   {
      return this.isValueSpecified;
   }

   @Override
   public String toString()
   {
      if (this.toString == null)
      {
         StringBuilder sb = new StringBuilder();
         
         sb.append('\n').append(this.getClass().getName()).append('@').append(this.hashCode());
         sb.append("\n{\n").append("   targetClass=").append(this.targetClass).append(",\n");
         sb.append("   targetName=").append(this.targetName).append(",\n");
         sb.append("   envEntryName=").append(this.envEntryName).append(",\n");
         sb.append("   envEntryType=").append(this.valueClass).append(",\n");
         sb.append("   performInjection=").append(this.isValueSpecified).append("\n}");
         
         this.toString = sb.toString();
      }
      
      return this.toString;
   }
   
}
