package de.thro.mis.containers.producer.models;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author Peter Kurfer
 * Created on 10/18/17.
 */
public class MessageDto {

	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof MessageDto)) return false;

		MessageDto that = (MessageDto) o;

		return getMessage() != null ? getMessage().equals(that.getMessage()) : that.getMessage() == null;
	}

	@Override
	public int hashCode() {
		return getMessage() != null ? getMessage().hashCode() : 0;
	}


	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("message", message)
				.toString();
	}
}
