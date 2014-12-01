/* Copyright (c) 2013, Richard Plangger <rich@pasra.at> All rights reserved.
 *
 * Android Record version 0.1.4 generated this file. For more
 * information see http://record.pasra.at/
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS
 * OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY
 * AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 *
 * This copyright notice must not be modified or deleted.
 */
// This file is generated. If you want to save you some time: !!!DO NOT MODIFY!!!
package com.getbro.meetmeandroid.generate;

import android.database.sqlite.SQLiteDatabase;
import at.pasra.record.RecordBuilder;
import com.getbro.meetmeandroid.generate.Account;
import com.getbro.meetmeandroid.generate.Event;
import com.getbro.meetmeandroid.generate.Keyword;

public class LocalSession{
    private SQLiteDatabase mDB;
    private final AccountRecord account_record = new AccountRecord();
    private final EventRecord event_record = new EventRecord();
    private final KeywordRecord keyword_record = new KeywordRecord();
    public LocalSession(SQLiteDatabase database){
        this.mDB = database;
    }
    public void saveAccount(Account obj){
        if (obj == null){
            throw new IllegalArgumentException("Tried to save an instance of Account which was null. Cannot do that!");
        }
        account_record.save(mDB, obj);
    }
    public Account findAccount(java.lang.Long id){
        if (id == null){
            throw new IllegalArgumentException("why would you want to load a account record with a null key?");
        }
        return account_record.load(mDB, id);
    }
    public void destroyAccount(Account obj){
        if (obj == null){
            throw new IllegalArgumentException("why would you want to delete a account record with a null obj?");
        }
        account_record.delete(mDB, obj.getId());
    }
    public AccountRecordBuilder queryAccounts(){
        return new AccountRecordBuilder(mDB);
    }
    public void saveEvent(Event obj){
        if (obj == null){
            throw new IllegalArgumentException("Tried to save an instance of Event which was null. Cannot do that!");
        }
        event_record.save(mDB, obj);
    }
    public Event findEvent(java.lang.Long id){
        if (id == null){
            throw new IllegalArgumentException("why would you want to load a event record with a null key?");
        }
        return event_record.load(mDB, id);
    }
    public void destroyEvent(Event obj){
        if (obj == null){
            throw new IllegalArgumentException("why would you want to delete a event record with a null obj?");
        }
        event_record.delete(mDB, obj.getId());
    }
    public EventRecordBuilder queryEvents(){
        return new EventRecordBuilder(mDB);
    }
    public void saveKeyword(Keyword obj){
        if (obj == null){
            throw new IllegalArgumentException("Tried to save an instance of Keyword which was null. Cannot do that!");
        }
        keyword_record.save(mDB, obj);
    }
    public Keyword findKeyword(java.lang.Long id){
        if (id == null){
            throw new IllegalArgumentException("why would you want to load a keyword record with a null key?");
        }
        return keyword_record.load(mDB, id);
    }
    public void destroyKeyword(Keyword obj){
        if (obj == null){
            throw new IllegalArgumentException("why would you want to delete a keyword record with a null obj?");
        }
        keyword_record.delete(mDB, obj.getId());
    }
    public KeywordRecordBuilder queryKeywords(){
        return new KeywordRecordBuilder(mDB);
    }
    public void clearCache(){
        account_record.clearCache();
        event_record.clearCache();
        keyword_record.clearCache();
    }
    public AccountRecord getAccountRecord(){
        return account_record;
    }
    public EventRecord getEventRecord(){
        return event_record;
    }
    public KeywordRecord getKeywordRecord(){
        return keyword_record;
    }
    public android.database.Cursor queryRaw(String query, String ... args){
        return mDB.rawQuery(query, args);
    }
}
