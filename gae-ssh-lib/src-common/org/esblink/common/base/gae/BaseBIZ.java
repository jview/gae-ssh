package org.esblink.common.base.gae;

import org.esblink.common.base.context.ModuleContext;
import org.esblink.common.base.context.UserContext;
import org.esblink.common.base.domain.IUser;
import org.esblink.common.base.i18n.IMessageSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseBIZ implements IBaseBIZ
{
protected Logger log = LoggerFactory.getLogger(getClass());

protected IMessageSource getMessageSource()
{
  return ModuleContext.getContext().getMessageSource();
}

protected Object getUserCached(Long userId, Long roleId)
{
  return UserContext.getContext().getUserCached(userId, roleId);
}

protected IUser getCurrentUser()
{
  return UserContext.getContext().getCurrentUser();
}
}