import { useEffect } from 'react';
import { Table } from 'antd';
import { ColumnsType } from 'antd/es/table';

import { HeaderActions } from 'app/components/header-actions/HeaderActions';
import { MainHeader } from 'app/components/main-header/MainHeader';
import { useAppDispatch, useAppSelector } from 'app/hooks/common';
import { fetchUsers, selectUsers } from 'app/pages/admin/admin-slice';
import { User } from 'app/model/User';
import { useNavigate } from 'react-router-dom';

import { IsaButton } from 'app/components/isa-button/IsaButton';

const tableColumns: ColumnsType<User> = [
  {
    title: 'Korisnik',
    dataIndex: 'username',
    key: 'username'
  },
  {
    title: 'Ime',
    dataIndex: 'firstname',
    key: 'name'
  },
  {
    title: 'Prezime',
    dataIndex: 'lastname',
    key: 'lastname'
  }
];

export const UsersPage = () => {
  const dispatch = useAppDispatch();
  const navigate = useNavigate();

  const users = useAppSelector(selectUsers);

  useEffect(() => {
    dispatch(fetchUsers());
  }, [dispatch]);

  const handleUserSelected = (id: number) => navigate(`/admin/users/${id}`);

  const handleCreateDiver = () => navigate(`/admin/users/create-driver`);

  return (
    <div className="d-flex flex-column flex-grow-1">
      <MainHeader>
        <HeaderActions />
      </MainHeader>
      <div className="my-3 d-flex flex-column flex-grow-1">
        <>
          <div className="mb-4 d-flex justify-content-between align-items-center">
            <h3 className="h3 m-0">Korisnici:</h3>
            <IsaButton type="primary" onClick={handleCreateDiver}>
              Kreiraj vozača
            </IsaButton>
          </div>
          {Boolean(users?.length) && (
            <Table
              dataSource={users}
              columns={tableColumns}
              pagination={false}
              onRow={({ id }) => ({ onClick: () => handleUserSelected(id) })}
            />
          )}
        </>
      </div>
    </div>
  );
};
