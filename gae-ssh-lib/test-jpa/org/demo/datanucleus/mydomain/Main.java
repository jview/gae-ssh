/**********************************************************************
Copyright (c) 2008 Andy Jefferson and others. All rights reserved.
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

Contributors:
    ...
**********************************************************************/
package org.demo.datanucleus.mydomain;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Transaction;

import org.demo.datanucleus.mydomain.usertypes.IPAddress;
import org.demo.datanucleus.mydomain.usertypes.Machine;

/**
 * Sample application persisting a persistable type which has an IPAddress field.
 */
public class Main
{
    public static void main(String[] args)
    {
        System.out.println("DataNucleus Samples : User-Types");
        System.out.println("================================");
        PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");

        System.out.println(">> Persisting Machine objects with a field of a user-defined type");
        PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx = pm.currentTransaction();
        Object id = null;
        try
        {
            tx.begin();

            Machine m1 = new Machine("nucleus1", new IPAddress("192.168.1.1"));
            Machine m2 = new Machine("nucleus2", new IPAddress("192.168.1.2"));
            pm.makePersistent(m1);
            pm.makePersistent(m2);
 
            tx.commit();
            id = JDOHelper.getObjectId(m1);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }

        pm = pmf.getPersistenceManager();
        tx = pm.currentTransaction();
        try
        {
            tx.begin();

            Machine m1 = (Machine)pm.getObjectById(id);
            System.out.println(">> Retrieved Machine : " + m1.toString());
 
            tx.commit();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
    }
}