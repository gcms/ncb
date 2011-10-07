package edu.fiu.strg.ACSTF.touchpoint;

public abstract class AbstractTouchPoint<AnyResource>
{
	protected AnyResource resource;

	
	public AbstractTouchPoint() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AbstractTouchPoint(AnyResource resource)
	{
		this.resource = resource;
	}

	public AbstractTouchPoint<AnyResource> getState()
	{
		return this;
	}

	public AnyResource getResource()
	{
		return resource;
	}
}
