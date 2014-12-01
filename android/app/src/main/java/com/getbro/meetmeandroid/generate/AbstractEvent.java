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

import com.getbro.meetmeandroid.generate.Event;
import com.getbro.meetmeandroid.generate.Keyword;
import at.pasra.record.RecordBuilder;

public class AbstractEvent{
    protected java.lang.String mUser;
    protected java.lang.Long mStartTime;
    protected java.lang.Long mRemoteId;
    protected java.lang.String mDescription;
    protected java.lang.Integer mDuration;
    protected java.lang.Integer mMaxAttending;
    protected java.lang.Integer mMinAttending;
    protected java.lang.Double mPrice;
    protected java.lang.Boolean mIsPublic;
    protected java.lang.String mAcceptState;
    protected java.lang.Long mId;
    
    public AbstractEvent(java.lang.Long id){
        this.mId = id;
        this.mUser = "";
        this.mStartTime = new Long(0L);
        this.mRemoteId = new Long(0L);
        this.mDescription = "";
        this.mDuration = new Integer(0);
        this.mMaxAttending = new Integer(0);
        this.mMinAttending = new Integer(0);
        this.mPrice = 0.0;
        this.mIsPublic = new Boolean(false);
        this.mAcceptState = "";
    }
    
    public java.lang.String getUser() { return mUser; }
    public void setUser(java.lang.String value) { mUser = value; }
    public java.lang.Long getStartTime() { return mStartTime; }
    public void setStartTime(java.lang.Long value) { mStartTime = value; }
    public java.lang.Long getRemoteId() { return mRemoteId; }
    public void setRemoteId(java.lang.Long value) { mRemoteId = value; }
    public java.lang.String getDescription() { return mDescription; }
    public void setDescription(java.lang.String value) { mDescription = value; }
    public java.lang.Integer getDuration() { return mDuration; }
    public void setDuration(java.lang.Integer value) { mDuration = value; }
    public java.lang.Integer getMaxAttending() { return mMaxAttending; }
    public void setMaxAttending(java.lang.Integer value) { mMaxAttending = value; }
    public java.lang.Integer getMinAttending() { return mMinAttending; }
    public void setMinAttending(java.lang.Integer value) { mMinAttending = value; }
    public java.lang.Double getPrice() { return mPrice; }
    public void setPrice(java.lang.Double value) { mPrice = value; }
    public java.lang.Boolean getIsPublic() { return mIsPublic; }
    public void setIsPublic(java.lang.Boolean value) { mIsPublic = value; }
    public java.lang.String getAcceptState() { return mAcceptState; }
    public void setAcceptState(java.lang.String value) { mAcceptState = value; }
    public java.lang.Long getId() { return mId; }
    public void setId(java.lang.Long value) { mId = value; }
    public RecordBuilder<Keyword> loadKeywords(LocalSession session){
        return session.queryKeywords().where("event_id = ?", Long.toString(mId) );
    }
    public static Event fromCursor(android.database.Cursor cursor){
        Event record = new Event();
        record.setUser(cursor.getString(cursor.getColumnIndex("user")));
        record.setStartTime(cursor.getLong(cursor.getColumnIndex("start_time")));
        record.setRemoteId(cursor.getLong(cursor.getColumnIndex("remote_id")));
        record.setDescription(cursor.getString(cursor.getColumnIndex("description")));
        record.setDuration(cursor.getInt(cursor.getColumnIndex("duration")));
        record.setMaxAttending(cursor.getInt(cursor.getColumnIndex("max_attending")));
        record.setMinAttending(cursor.getInt(cursor.getColumnIndex("min_attending")));
        record.setPrice(Double.longBitsToDouble(cursor.getLong(cursor.getColumnIndex("price"))));
        record.setIsPublic((cursor.getInt(cursor.getColumnIndex("is_public")) != 0));
        record.setAcceptState(cursor.getString(cursor.getColumnIndex("accept_state")));
        record.setId(cursor.getLong(cursor.getColumnIndex("_id")));
        return record;
    }
}
