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
package com.gospry.generate;

public class AbstractKeyword {
    protected java.lang.String mText;
    protected java.lang.Long mEventId;
    protected java.lang.Long mId;

    public AbstractKeyword(java.lang.Long id) {
        this.mId = id;
        this.mText = "";
        this.mEventId = new Long(0L);
    }

    public java.lang.String getText() {
        return mText;
    }

    public void setText(java.lang.String value) {
        mText = value;
    }

    public java.lang.Long getEventId() {
        return mEventId;
    }

    public void setEventId(java.lang.Long value) {
        mEventId = value;
    }

    public java.lang.Long getId() {
        return mId;
    }

    public void setId(java.lang.Long value) {
        mId = value;
    }

    public Event loadEvent(LocalSession session) {
        return session.findEvent(this.getId());
    }

    public static Keyword of(Event obj0) {
        Keyword obj = new Keyword();
        obj.setEventId(obj0.getId());
        return obj;
    }

    public static Keyword fromCursor(android.database.Cursor cursor) {
        Keyword record = new Keyword();
        record.setText(cursor.getString(cursor.getColumnIndex("text")));
        record.setEventId(cursor.getLong(cursor.getColumnIndex("event_id")));
        record.setId(cursor.getLong(cursor.getColumnIndex("_id")));
        return record;
    }
}
