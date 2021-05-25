package com.practice.fileuploadexample.dao;

import com.practice.fileuploadexample.model.UserDetail;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDAOImpl implements UserDAO {

    @Autowired
    SessionFactory sessionFactory;

    public int saveUser(UserDetail userDetail) {

        Session session = null;
        try {
            session = sessionFactory.getCurrentSession();
            int status = (Integer) session.save(userDetail);
            return status;
        } catch (Exception exception) {
            System.out.println("Exception in saving data into the database" + exception);
            return 0;
        } finally {
            session.flush();
        }
    }

    public UserDetail getUserDetail(int userId) {
        Session session = null;
        try
        {
            session = sessionFactory.getCurrentSession();
            UserDetail userDetail = session.get(UserDetail.class, userId);
            return userDetail;
        }
        catch(Exception exception)
        {
            System.out.println("Exception in saving data into the database " + exception);
            return null;
        }
        finally
        {
            session.flush();
        }
    }

    public int updateProfileImage(String profileImage, int userID) {
        Session session= sessionFactory.getCurrentSession();
        int result;
        try
        {
            Query<UserDetail> query = session.createQuery("update UserDetail set profileImage = :profileImage where userId=:userID ");
            query.setParameter("profileImage", profileImage);
            query.setParameter("userID", userID);
            result = query.executeUpdate();
            if(result > 0)
            {
                return result;
            }
            else return -5;
        }
        catch(Exception exception)
        {
            System.out.println("Error while updating profileImage from DAO :: " + exception.getMessage());
            return -5;
        }
        finally
        {
            session.flush();
        }
    }

    @Override
    public List<UserDetail> getUsers() {
        Session session= sessionFactory.getCurrentSession();
        Query<UserDetail> query = session.createQuery("from UserDetail", UserDetail.class);
        List<UserDetail> list = query.getResultList();
        return list;
    }
}
