/* Copyright (c) 2013, Richard Plangger <rich@pasra.at> All rights reserved.
 *
 * Android Record version 0.1.0 generated this file. For more
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

import at.pasra.record.RecordBuilder;

public class AbstractSettings{
    protected java.lang.String mNumber;
    protected java.lang.String mSecret;
    protected java.lang.Long mId;
    
    public AbstractSettings(java.lang.Long id){
        this.mId = id;
        this.mNumber = "";
        this.mSecret = "";
    }
    
    public java.lang.String getNumber() { return mNumber; }
    public void setNumber(java.lang.String value) { mNumber = value; }
    public java.lang.String getSecret() { return mSecret; }
    public void setSecret(java.lang.String value) { mSecret = value; }
    public java.lang.Long getId() { return mId; }
    public void setId(java.lang.Long value) { mId = value; }
    public static Settings fromCursor(android.database.Cursor cursor){
        Settings record = new Settings();
        record.setNumber(cursor.getString(cursor.getColumnIndex("number")));
        record.setSecret(cursor.getString(cursor.getColumnIndex("secret")));
        record.setId(cursor.getLong(cursor.getColumnIndex("_id")));
        return record;
    }
}
