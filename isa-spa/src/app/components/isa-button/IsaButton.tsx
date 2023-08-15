import React, { ReactNode } from 'react';
import { Button } from 'antd';
import { ButtonHTMLType, ButtonShape, ButtonSize, ButtonType } from 'antd/es/button';

interface Props {
  children?: string | JSX.Element | JSX.Element[];
  type?: ButtonType;
  shape?: ButtonShape;
  icon?: ReactNode;
  disabled?: boolean;
  block?: boolean;
  size?: ButtonSize;
  className?: string;
  htmlType?: ButtonHTMLType;
  onClick?: React.MouseEventHandler;
}

export const IsaButton = ({
  className,
  children,
  type,
  shape,
  icon,
  disabled,
  block,
  size,
  htmlType,
  onClick
}: Props) => {
  return (
    <Button
      className={className}
      type={type}
      shape={shape}
      size={size}
      icon={icon}
      disabled={disabled}
      block={block}
      htmlType={htmlType}
      onClick={onClick}
    >
      {children}
    </Button>
  );
};
